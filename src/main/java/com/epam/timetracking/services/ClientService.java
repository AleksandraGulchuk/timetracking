package com.epam.timetracking.services;

import com.epam.timetracking.dto.ActivityDTO;
import com.epam.timetracking.dto.DeniedRequest;
import com.epam.timetracking.entities.*;
import com.epam.timetracking.exceptions.ServiceException;

import java.util.List;

public interface ClientService {

    User getUser(String login, String password) throws ServiceException;

    List<Category> getCategories() throws ServiceException;

    List<Activity> getActivities(int userId) throws ServiceException;

    List<ActivityStatus> getActivityStatuses() throws ServiceException;

    Activity getActivity(int activityId) throws ServiceException;

    void sentRequestDeleteActivity(User user, Activity activity) throws ServiceException;

    void sentRequestAppendActivityTime(User user, Activity activity) throws ServiceException;

    void sentRequestCreateActivity(User user, Activity activity) throws ServiceException;

    List<DeniedRequest> getDeniedRequests(int userId) throws ServiceException;
}
