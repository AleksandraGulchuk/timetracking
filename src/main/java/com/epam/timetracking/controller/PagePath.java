package com.epam.timetracking.controller;

/**
 * The page path holder.
 *
 */
public class PagePath {

    public static final String LOGIN = "/index.jsp";
    public static final String ERROR = "WEB-INF/views/error.jsp";
    public static final String MESSAGE = "WEB-INF/views/message.jsp";
    public static final String START_PAGE_COMMAND = "controller?command=startPageCommand";

    public static final String ADMIN_START = "WEB-INF/views/admin.jsp";
    public static final String REQUESTS = "WEB-INF/views/requests.jsp";
    public static final String MANAGEMENT = "WEB-INF/views/management.jsp";
    public static final String USER_ACTIVITIES = "WEB-INF/views/userActivities.jsp";
    public static final String REPORTS = "WEB-INF/views/reports.jsp";
    public static final String GO_TO_MANAGEMENT_COMMAND = "controller?command=goToManagement";
    public static final String SHOW_USER_ACTIVITIES_COMMAND = "controller?command=showUserActivities&userId=";

    public static final String CLIENT_START = "WEB-INF/views/client.jsp";
    public static final String ACTIVITY = "WEB-INF/views/activity.jsp";
    public static final String DENIED_REQUESTS = "WEB-INF/views/deniedRequests.jsp";


    public static final String GO_TO_ACTIVITY_COMMAND = "controller?command=goToActivity&activityId=";

}
