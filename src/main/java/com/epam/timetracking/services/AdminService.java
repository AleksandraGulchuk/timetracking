package com.epam.timetracking.services;

import com.epam.timetracking.entities.UserRequest;
import com.epam.timetracking.entities.*;
import com.epam.timetracking.exceptions.ServiceException;

import java.util.List;

public interface AdminService {

    List<User> getUsers() throws ServiceException;

    List<Activity> getActivities() throws ServiceException;

    List<Role> getRoles() throws ServiceException;

    void addCategory(Category category) throws ServiceException;
    void deleteCategory(int categoryId) throws ServiceException;

    void addUser(User user) throws ServiceException;
    void deleteUser(int userId) throws ServiceException;

    void addActivity(Activity activity, int userId) throws ServiceException;

    void deleteActivity(int activityId) throws ServiceException;

    List<UserRequest> getUserRequests() throws ServiceException;

    void processUserRequest(String requestType, int requestId, String choose, String comment) throws ServiceException;

}
