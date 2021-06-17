package com.epam.timetracking.service.database;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.bean.ActivityDTO;
import com.epam.timetracking.pojo.bean.DeniedRequest;
import com.epam.timetracking.pojo.entity.*;
import com.epam.timetracking.service.ClientService;
import com.epam.timetracking.service.database.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DBClientService implements ClientService {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private static final Logger log = LogManager.getLogger(DBClientService.class);

    public DBClientService(DBConfig dbConfig) {
        jdbcTemplate = new JdbcTemplate();
        dataSource = dbConfig.getDataSource();
    }

    @Override
    public List<Category> getCategories() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection, SQLQueries.SELECT_CATEGORIES,
                    new ResultSetRowMapper<>(Category.class));
        } catch (SQLException exception) {
            log.error("Cannot get categories: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get categories!", exception);
        }
    }

    @Override
    public List<Activity> getActivities(int userId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection,
                    SQLQueries.SELECT_ACTIVITIES_BY_USER_ID,
                    new Integer[]{userId},
                    new ResultSetRowMapper<>(Activity.class));
        } catch (SQLException exception) {
            log.error("Cannot get activities: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get activities!", exception);
        }
    }

    @Override
    public Activity getActivity(int activityId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            Activity activity = jdbcTemplate.queryOne(connection, SQLQueries.SELECT_AN_ACTIVITY_BY_ID,
                    new Integer[]{activityId},
                    new ResultSetRowMapper<>(Activity.class));
            List<ActivityStory> stories = getActivityStories(activityId);
            activity.setStories(stories);
            return activity;
        } catch (SQLException exception) {
            log.error("Cannot get activity: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get activity!", exception);
        }
    }

    private List<ActivityStory> getActivityStories(int activityId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection,
                    SQLQueries.SELECT_ACTIVITY_STORIES_BY_ACTIVITY_ID,
                    new Integer[]{activityId},
                    new ResultSetRowMapper<>(ActivityStory.class)
            );
        } catch (SQLException exception) {
            log.error("Cannot get activity stories: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get activity stories!", exception);
        }
    }

    @Override
    public List<ActivityStatus> getActivityStatuses() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection,
                    SQLQueries.SELECT_ACTIVITY_STATUSES,
                    new ResultSetRowMapper<>(ActivityStatus.class)
            );
        } catch (SQLException exception) {
            log.error("Cannot get activity statuses: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get activity statuses!", exception);
        }
    }

    @Override
    public User getUser(String login, String password) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            User user = jdbcTemplate.queryOne(connection, SQLQueries.SELECT_A_USER_BY_LOGIN_AND_PASSWORD,
                    new String[]{login, password},
                    new ResultSetRowMapper<>(User.class));
            if (user == null) {
                throw new ServiceException("User not found by login and password!");
            }
            List<Activity> activities = getActivities(user.getId());
            user.setActivities(new Adapter<>(Activity.class, ActivityDTO.class).adaptList(activities));
            return user;
        } catch (SQLException exception) {
            log.error("User not found by login and password: " + exception.getMessage(), exception);
            throw new ServiceException("User not found by login and password!", exception);
        }
    }

    @Override
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

    @Override
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


    @Override
    public void sendRequestDeleteActivity(User user, Activity activity) throws ServiceException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            int activityOldStatusId = getActivityStatusId(activity.getStatus(), connection);

            jdbcTemplate.update(connection, SQLQueries.INSERT_USER_DELETE_REQUEST,
                    new Object[]{LocalDateTime.now(),
                            user.getId(),
                            activity.getId(),
                            activity.getComment(),
                            activityOldStatusId});

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

    @Override
    public void appendActivityTime(User user, Activity activity) throws ServiceException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            log.debug("activity --> " + activity);

            long totalTime = jdbcTemplate.queryOne(connection,
                    SQLQueries.SELECT_TOTAL_TIME_FROM_ACTIVITIES_BY_ID,
                    new Integer[]{activity.getId()}, Fields.getLongRowMapper(Fields.TOTAL_TIME));

            long resultTime = activity.getAppendTime() + totalTime;
            LocalDateTime dateTimeTransaction = LocalDateTime.now();
            jdbcTemplate.update(connection, SQLQueries.APPEND_TIME_ACTIVITY,
                    new Object[]{resultTime, dateTimeTransaction, activity.getId()});

            jdbcTemplate.update(connection, SQLQueries.INSERT_CREATE_ACTIVITY_STORY_APPEND_TIME,
                    new Object[]{activity.getId(), dateTimeTransaction, activity.getAppendTime(), activity.getComment()});

            connection.commit();
        } catch (SQLException exception) {
            log.error("Failed to send a request to delete activity: " + exception.getMessage(), exception);
            rollbackTransaction(connection);
            throw new ServiceException("Failed to send a request to delete activity!" + exception.getMessage());
        } finally {
            JdbcTemplate.closeResources(connection);
        }
    }

    private int getActivityStatusId(String status, Connection connection) throws SQLException {
        return jdbcTemplate.queryOne(connection,
                SQLQueries.SELECT_STATUS_ID_BY_STATUS,
                new String[]{status},
                Fields.getIntegerRowMapper(Fields.ID));
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
