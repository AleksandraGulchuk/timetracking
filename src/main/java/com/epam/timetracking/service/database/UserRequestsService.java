package com.epam.timetracking.service.database;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.dto.DeniedRequest;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.pojo.entity.UserRequest;
import com.epam.timetracking.service.database.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserRequestsService {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private static final Logger log = LogManager.getLogger(UserRequestsService.class);

    public UserRequestsService(DBConfig dbConfig) {
        jdbcTemplate = new JdbcTemplate();
        dataSource = dbConfig.getDataSource();
    }

        /**
     * Sends a user request to create a new activity.
     *
     * @param user     - User object.
     * @param activity - Activity object.
     */
    public void sendRequestCreateActivity(User user, Activity activity) throws ServiceException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            int categoryId = jdbcTemplate.queryOne(connection,
                    SQLQueries.SELECT_CATEGORY_ID_BY_CATEGORY,
                    new Object[]{activity.getCategory()},
                    Fields.getIntegerRowMapper(Fields.ID)
            );
            jdbcTemplate.update(connection, SQLQueries.INSERT_USER_CREATE_REQUEST,
                    new Object[]{user.getId(),
                            LocalDateTime.now(),
                            categoryId,
                            activity.getTitle(),
                            activity.getDescription(),
                            activity.getTotalTime(),
                            activity.getComment()});
            connection.commit();
        } catch (SQLException exception) {
            log.error("Failed to send a request to create activity: " + exception.getMessage(), exception);
            rollbackTransaction(connection);
            throw new ServiceException("Failed to send a request to create activity!", exception);
        } finally {
            if (connection != null) {
                JdbcTemplate.closeResources(connection);
            }
        }
    }

    /**
     * Gets a list of all DeniedRequest objects by user id.
     *
     * @param userId - user id.
     * @return - a list of all DeniedRequest objects owned by the user with userId.
     */
    public List<DeniedRequest> getDeniedRequests(int userId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection, SQLQueries.SELECT_DENIED_REQUESTS,
                    new Integer[]{userId},
                    new ResultSetRowMapper<>(DeniedRequest.class));
        } catch (SQLException | NullPointerException exception) {
            log.error("User not found by login and password: " + exception.getMessage(), exception);
            throw new ServiceException("User not found by login and password!", exception);
        }
    }

        /**
     * Sends user request to delete activity.
     *
     * @param user     - User object.
     * @param activity - Activity object.
     */
    public void sendRequestDeleteActivity(User user, Activity activity) throws ServiceException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            jdbcTemplate.update(connection, SQLQueries.INSERT_USER_DELETE_REQUEST,
                    new Object[]{LocalDateTime.now(),
                            user.getId(),
                            activity.getId(),
                            activity.getComment(),
                    });

            jdbcTemplate.update(connection,
                    SQLQueries.SET_STATUS_ON_UPDATE_ACTIVITY,
                    new Integer[]{activity.getId()});
            connection.commit();
        } catch (SQLException exception) {
            log.error("Failed to send a request to create activity: " + exception.getMessage(), exception);
            rollbackTransaction(connection);
            throw new ServiceException("Failed to send a request to delete activity!");
        } finally {
            JdbcTemplate.closeResources(connection);
        }
    }

    /**
     * Gets a list of all UserRequest objects.
     *
     * @return - a list of all UserRequest objects.
     */
    public List<UserRequest> getUserRequests() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            List<UserRequest> userDeleteRequests =
                    jdbcTemplate.query(connection, SQLQueries.SELECT_USERS_DELETE_REQUESTS,
                            new ResultSetRowMapper<>(UserRequest.class));


            List<UserRequest> userCreateRequests =
                    jdbcTemplate.query(connection, SQLQueries.SELECT_USERS_CREATE_REQUESTS,
                            new ResultSetRowMapper<>(UserRequest.class));

            List<UserRequest> userRequests = new ArrayList<>();
            userRequests.addAll(userDeleteRequests);
            userRequests.addAll(userCreateRequests);
            return userRequests;
        } catch (SQLException exception) {
            log.error("Cannot get user requests: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get user requests: " + exception.getMessage(), exception);
        }
    }

    /**
     * Processes user request.
     *
     * @param userRequest - UserRequest object.
     * @param choice      - contains the selected version of the user's request processing.
     */
    public void processUserRequest(UserRequest userRequest, String choice) throws ServiceException {
        log.trace("request: " + userRequest + " choose: " + choice);

        switch (userRequest.getRequestType()) {
            case "create NEW":
                addNewActivity(userRequest.getId(), choice, userRequest.getComment());
                break;
            case "delete":
                deleteActivity(userRequest.getId(), choice, userRequest.getComment());
                break;
            default:
                log.error("RequestType was not processed. RequestType: ");
                break;
        }
        log.trace("process user request finished");
    }

    private void addNewActivity(int requestId, String choose, String comment) throws ServiceException {
        Integer[] reqIdParam = new Integer[]{requestId};
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if ("confirm".equals(choose)) {
                log.debug("addNewActivity - start confirm transaction");
                jdbcTemplate.update(connection, SQLQueries.INSERT_USER_ACTIVITY_BY_REQUEST_ID, reqIdParam);
                jdbcTemplate.update(connection, SQLQueries.INSERT_CREATE_ACTIVITY_STORY_CREATE, reqIdParam);
                jdbcTemplate.update(connection, SQLQueries.DELETE_USER_REQUEST_NEW_ACTIVITY_FROM_CREATE_REQUESTS, reqIdParam);
                connection.commit();
                log.debug("addNewActivity - end confirm transaction");
            }
            if ("deny".equals(choose)) {
                log.debug("addNewActivity - start deny transaction");
                jdbcTemplate.update(connection, SQLQueries.INSERT_MESSAGE_DENY_CREATE_ACTIVITY_REQUEST, new Object[]{1, comment, requestId});
                jdbcTemplate.update(connection, SQLQueries.DELETE_USER_REQUEST_NEW_ACTIVITY_FROM_CREATE_REQUESTS, reqIdParam);
                connection.commit();
                log.debug("addNewActivity - end deny transaction");
            }
        } catch (SQLException exception) {
            rollbackTransaction(connection);
            log.error("Cannot add new activity: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot add new activity: " + exception.getMessage(), exception);
        } finally {
            JdbcTemplate.closeResources(connection);
        }
    }

    private void deleteActivity(int requestId, String choose, String comment) throws ServiceException {
        Integer[] reqIdParam = new Integer[]{requestId};
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if ("confirm".equals(choose)) {
                log.debug("deleteActivity - start confirm transaction");
                jdbcTemplate.update(connection, SQLQueries.DELETE_USER_ACTIVITY_BY_REQUEST_ID, reqIdParam);
                jdbcTemplate.update(connection, SQLQueries.DELETE_USER_REQUEST_DELETE_ACTIVITY_FROM_DELETE_REQUESTS, reqIdParam);
                connection.commit();
            } else if ("deny".equals(choose)) {
                log.debug("deleteActivity - start deny transaction");
                jdbcTemplate.update(connection, SQLQueries.INSERT_MESSAGE_DENY_DELETE_ACTIVITY_REQUEST, new Object[]{2, comment, requestId});

                jdbcTemplate.update(connection, SQLQueries.SET_OLD_STATUS_ACTIVITY_FROM_DELETE_REQUESTS, new Integer[]{requestId, requestId});

                jdbcTemplate.update(connection, SQLQueries.DELETE_USER_REQUEST_DELETE_ACTIVITY_FROM_DELETE_REQUESTS, reqIdParam);
                connection.commit();
            }
        } catch (SQLException exception) {
            rollbackTransaction(connection);
            log.error("Cannot delete activity: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot delete activity: " + exception.getMessage(), exception);
        }
    }

    private void rollbackTransaction(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error("Cannot rollback transaction: " + e.getMessage(), e);
        }
    }

}
