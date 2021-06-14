package com.epam.timetracking.service.database;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.*;
import com.epam.timetracking.service.AdminService;
import com.epam.timetracking.service.database.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBAdminService implements AdminService {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private static final Logger log = LogManager.getLogger(DBAdminService.class);

    public DBAdminService(DBConfig dbConfig) {
        jdbcTemplate = new JdbcTemplate();
        dataSource = dbConfig.getDataSource();
    }

    @Override
    public List<User> getUsers() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection, SQLQueries.SELECT_USERS,
                    new ResultSetRowMapper<>(User.class));
        } catch (SQLException exception) {
            log.error("Cannot get users: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get users: " + exception.getMessage(), exception);
        }
    }

    @Override
    public List<Role> getRoles() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection, SQLQueries.SELECT_ROLES,
                    new ResultSetRowMapper<>(Role.class));
        } catch (SQLException exception) {
            log.error("Cannot get roles: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get roles: " + exception.getMessage(), exception);
        }
    }

    @Override
    public List<Activity> getActivities() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            List<Activity> activities = jdbcTemplate.query(connection, SQLQueries.SELECT_ACTIVITIES,
                    new ResultSetRowMapper<>(Activity.class));
            activities.forEach(activity -> activity.setStories(getStories(connection, activity.getId())));
            return activities;
        } catch (SQLException exception) {
            log.error("Cannot get activities: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get activities: " + exception.getMessage(), exception);
        }
    }

    private List<ActivityStory> getStories(Connection connection, int activityId) {
        try {
            return jdbcTemplate.query(
                    connection,
                    SQLQueries.SELECT_ACTIVITY_STORIES_BY_ACTIVITY_ID,
                    new Integer[]{activityId},
                    new ResultSetRowMapper<>(ActivityStory.class));
        } catch (SQLException exception) {
            log.error("Cannot get story: " + exception.getMessage(), exception);
        }
        return new ArrayList<>();
    }


    @Override
    public void addCategory(Category category) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(connection, SQLQueries.INSERT_CATEGORY, new Object[]{category.getCategory()});
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot add category: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot add category: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void deleteCategory(int categoryId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(connection, SQLQueries.DELETE_CATEGORY_BY_ID,
                    new Integer[]{categoryId});
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot delete category: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot delete category: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void addUser(User user) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(
                    connection,
                    SQLQueries.INSERT_USER,
                    new Object[]{
                            user.getName(),
                            user.getLogin(),
                            user.getPassword(),
                            user.getRoleId()
                    });
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot add user: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot add user: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void deleteUser(int userId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(connection, SQLQueries.DELETE_USER_BY_ID,
                    new Integer[]{userId});
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot delete user: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot delete user: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void addActivity(Activity activity, int userId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(connection, SQLQueries.INSERT_ACTIVITY,
                    new Object[]{
                            userId,
                            activity.getCategoryId(),
                            activity.getTitle(),
                            activity.getDescription(),
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            activity.getTotalTime(),
                            1
                    });
            int activityId = jdbcTemplate.queryOne(connection,
                    SQLQueries.SELECT_LAST_VALUE_ACTIVITY_ID,
                    Fields.getIntegerRowMapper(Fields.LAST_VALUE));
            jdbcTemplate.update(connection, SQLQueries.INSERT_ACTIVITY_STORY_ADD,
                    new Object[]{
                            activityId,
                            LocalDateTime.now(),
                            activity.getTotalTime(),
                            "Create new activity (admin)"
                    });
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot add activity: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot add activity: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void deleteActivity(int activityId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(connection, SQLQueries.DELETE_ACTIVITY_BY_ID,
                    new Integer[]{activityId});
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot delete activity: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot delete activity: " + exception.getMessage(), exception);
        }
    }

    @Override
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

    @Override
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

    private void rollbackTransaction(Connection connection) throws ServiceException {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error("Cannot rollback transaction: " + e.getMessage(), e);
            throw new ServiceException("Cannot rollback transaction: " + e.getMessage(), e);
        }
    }

}
