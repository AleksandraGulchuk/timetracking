package com.epam.timetracking.service.database;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.ActivityStatus;
import com.epam.timetracking.pojo.entity.ActivityStory;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.database.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityService {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private static final Logger log = LogManager.getLogger(ActivityService.class);

    public ActivityService(DBConfig dbConfig) {
        jdbcTemplate = new JdbcTemplate();
        dataSource = dbConfig.getDataSource();
    }

    /**
     * Gets a list of all Activity objects.
     *
     * @return - a list of all Activity objects.
     */
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

    /**
     * Gets an Activity object by id.
     *
     * @param activityId - activity id.
     * @return - an Activity object by its id.
     */
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

    /**
     * Gets a list of all ActivityStatus objects.
     *
     * @return - a list of all ActivityStatus objects.
     */
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

    /**
     * Adds new Activity object assigned to the user with userId.
     *
     * @param activity - Activity object.
     * @param userId   - user id.
     */
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

    /**
     * Deletes Activity object by id.
     *
     * @param activityId - activity id.
     */
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

    /**
     * Adds time to user activity.
     *
     * @param user     - User object.
     * @param activity - Activity object.
     */
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
