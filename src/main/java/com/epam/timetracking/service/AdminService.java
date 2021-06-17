package com.epam.timetracking.service;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.*;

import java.util.List;

/**
 * Interface for the Strategy pattern implementation.
 * Provides a service for executing admin commands.
 */
public interface AdminService {

    /**
     * Gets a list of all User objects.
     *
     * @return - a list of all User objects.
     */
    List<User> getUsers() throws ServiceException;

    /**
     * Gets a list of all Activity objects.
     *
     * @return - a list of all Activity objects.
     */
    List<Activity> getActivities() throws ServiceException;

    /**
     * Gets a list of all Role objects.
     *
     * @return - a list of all Role objects.
     */
    List<Role> getRoles() throws ServiceException;

    /**
     * Adds new Category object.
     *
     * @param category - Category object.
     */
    void addCategory(Category category) throws ServiceException;

    /**
     * Deletes Category object by id.
     *
     * @param categoryId - category id.
     */
    void deleteCategory(int categoryId) throws ServiceException;

    /**
     * Adds new User object.
     *
     * @param user - User object.
     */
    void addUser(User user) throws ServiceException;

    /**
     * Deletes User object by id.
     *
     * @param userId - user id.
     */
    void deleteUser(int userId) throws ServiceException;

    /**
     * Adds new Activity object assigned to the user with userId.
     *
     * @param activity - Activity object.
     * @param userId   - user id.
     */
    void addActivity(Activity activity, int userId) throws ServiceException;

    /**
     * Deletes Activity object by id.
     *
     * @param activityId - activity id.
     */
    void deleteActivity(int activityId) throws ServiceException;

    /**
     * Gets a list of all UserRequest objects.
     *
     * @return - a list of all UserRequest objects.
     */
    List<UserRequest> getUserRequests() throws ServiceException;

    /**
     * Processes user request.
     *
     * @param userRequest - UserRequest object.
     * @param choice      - contains the selected version of the user's request processing.
     */
    void processUserRequest(UserRequest userRequest, String choice) throws ServiceException;

}
