package com.epam.timetracking.service;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.bean.DeniedRequest;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.ActivityStatus;
import com.epam.timetracking.pojo.entity.Category;
import com.epam.timetracking.pojo.entity.User;

import java.util.List;

/**
 * Interface for the Strategy pattern implementation.
 * Provides a service for executing client commands.
 */
public interface ClientService {

    /**
     * Gets User object by login and password.
     *
     * @param login    - user login.
     * @param password - user password.
     * @return - User object.
     */
    User getUser(String login, String password) throws ServiceException;

    /**
     * Gets a list of all Category objects.
     *
     * @return - a list of all Category objects.
     */
    List<Category> getCategories() throws ServiceException;

    /**
     * Gets a list of all Activity objects by user id.
     *
     * @param userId - user id.
     * @return - a list of all Activity objects assigned to the user with userId.
     */
    List<Activity> getActivities(int userId) throws ServiceException;

    /**
     * Gets a list of all ActivityStatus objects.
     *
     * @return - a list of all ActivityStatus objects.
     */
    List<ActivityStatus> getActivityStatuses() throws ServiceException;

    /**
     * Gets an Activity object by id.
     *
     * @param activityId - activity id.
     * @return - an Activity object by its id.
     */
    Activity getActivity(int activityId) throws ServiceException;

    /**
     * Sends user request to delete activity.
     *
     * @param user     - User object.
     * @param activity - Activity object.
     */
    void sendRequestDeleteActivity(User user, Activity activity) throws ServiceException;

    /**
     * Sends a user request to create a new activity.
     *
     * @param user     - User object.
     * @param activity - Activity object.
     */
    void sendRequestCreateActivity(User user, Activity activity) throws ServiceException;

    /**
     * Adds time to user activity.
     *
     * @param user     - User object.
     * @param activity - Activity object.
     */
    void appendActivityTime(User user, Activity activity) throws ServiceException;

    /**
     * Gets a list of all DeniedRequest objects by user id.
     *
     * @param userId - user id.
     * @return - a list of all DeniedRequest objects owned by the user with userId.
     */
    List<DeniedRequest> getDeniedRequests(int userId) throws ServiceException;
}
