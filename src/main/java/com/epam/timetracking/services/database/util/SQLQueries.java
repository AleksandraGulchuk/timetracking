package com.epam.timetracking.services.database.util;

/**
 * SQLQueries, database: Postgres
 */

public class SQLQueries {

    public static final String SELECT_A_USER_BY_LOGIN_AND_PASSWORD =
            "SELECT users.id, name, login, role FROM users " +
                    " join roles on roles.id = users.role_id WHERE login = ? and password = ?";

    public static final String SELECT_CATEGORIES = "SELECT id, category FROM categories";

    public static final String SELECT_ACTIVITY_STATUSES = "SELECT id, status FROM statuses";

    public static final String SELECT_ACTIVITIES = "SELECT name as user_name, activities.id, category, " +
            "title, description, creation_date_time, " +
            " last_update_date_time, total_time, status " +
            " FROM activities " +
            " JOIN users u on u.id = activities.user_id " +
            " JOIN categories c on c.id = activities.category_id " +
            " JOIN statuses s on s.id = activities.status_id";

    public static final String SELECT_CATEGORY_ID_BY_CATEGORY = "SELECT id FROM categories WHERE category = ?";

    public static final String SELECT_ACTIVITIES_BY_USER_ID =
            "SELECT activities.id, categories.category, title, description, " +
                    "creation_date_time, last_update_date_time, total_time, statuses.status " +
                    "FROM activities " +
                    "join categories on activities.category_id = categories.id " +
                    "join statuses on activities.status_id = statuses.id " +
                    "WHERE user_id = ?";

    public static final String SELECT_AN_ACTIVITY_BY_ID =
            "SELECT " +
                    "activities.id, category_id, categories.category, title, description, " +
                    "creation_date_time, last_update_date_time, total_time, statuses.status " +
                    "FROM activities " +
                    "join categories on activities.category_id = categories.id " +
                    "join statuses on activities.status_id = statuses.id " +
                    "WHERE activities.id = ?";

    public static final String SELECT_ACTIVITY_STORIES_BY_ACTIVITY_ID =
            "SELECT " +
                    "update_date_time, time_spent, comment " +
                    "FROM activity_stories WHERE activity_id = ?";

    public static final String INSERT_USER_CREATE_REQUEST =
            "INSERT INTO users_create_activity_requests " +
                    "(user_id, request_date_time, category_id, title, description, total_time, comment) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";


    public static final String SELECT_STATUS_ID_BY_STATUS = "SELECT id from statuses WHERE status = ?";

    public static final String INSERT_USER_APPEND_TIME_REQUEST =
            "INSERT INTO users_append_time_activity_requests " +
                    "(user_id, activity_id, append_time, comment, request_date_time, activity_old_status_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";


    public static final String INSERT_USER_DELETE_REQUEST =
            "INSERT INTO users_delete_activity_requests " +
                    "(request_date_time, user_id, activity_id, comment, activity_old_status_id) " +
                    "VALUES (?, ?, ?, ?, ?)";


    public static final String SET_STATUS_ON_UPDATE_ACTIVITY =
            "UPDATE activities SET status_id = 3 WHERE id = ?";

    public static final String SET_OLD_STATUS_ACTIVITY_FROM_APPEND_TIME_REQUESTS =
            "UPDATE activities SET status_id = " +
                    "(SELECT activity_old_status_id FROM users_append_time_activity_requests WHERE id = ?) " +
            "WHERE id = (SELECT activity_id FROM users_append_time_activity_requests WHERE id = ?)";


    public static final String SET_OLD_STATUS_ACTIVITY_FROM_DELETE_REQUESTS =
            "UPDATE activities SET status_id = " +
                    "(SELECT activity_old_status_id FROM users_delete_activity_requests WHERE id = ?) " +
                    "WHERE id = (SELECT activity_id FROM users_delete_activity_requests WHERE id = ?)";


    public static final String SELECT_USERS_DELETE_REQUESTS =
            "SELECT delete.id as id, request_date_time, u.name as user_name, " +
                    "a.id as activity_id, c.category, a.title, a.description," +
                    "a.total_time, s.status, delete.comment, 'delete' as request_type " +
                    "FROM users_delete_activity_requests delete " +
                    "join users u on delete.user_id = u.id " +
                    "join activities a on a.id = delete.activity_id " +
                    "join categories c on a.category_id = c.id " +
                    "join statuses s on a.status_id = s.id";

    public static final String SELECT_USERS_APPEND_TIME_REQUESTS =
            "SELECT append.id as id, request_date_time as request_date_time, " +
                    "u.name as user_name, a.id as activity_id, c.category, a.title, a.description, " +
                    "a.total_time, s.status, append.comment, 'append time' as request_type, append_time " +
                    "FROM users_append_time_activity_requests append " +
                    "join users u on append.user_id = u.id " +
                    "join activities a on a.id = append.activity_id " +
                    "join categories c on a.category_id = c.id " +
                    "join statuses s on a.status_id = s.id";

    public static final String SELECT_USERS_CREATE_REQUESTS =
            "SELECT cr.id as id, u.name as user_name, " +
                    "c.category, cr.title, cr.description, cr.request_date_time, " +
                    "cr.total_time, 'create NEW' as request_type, " +
                    "cr.total_time as append_time, cr.comment " +
                    "FROM users_create_activity_requests cr " +
                    "join users u on cr.user_id = u.id " +
                    "join categories c on cr.category_id = c.id";

    public static final String INSERT_USER_ACTIVITY_BY_REQUEST_ID =
            "INSERT INTO activities " +
                    "(user_id, category_id, title, description, creation_date_time, " +
                    "last_update_date_time, total_time, status_id) " +
                    "SELECT user_id, category_id, title, description, " +
                    "request_date_time, request_date_time, total_time, 1 " +
                    "FROM users_create_activity_requests " +
                    "WHERE id = ?";
    public static final String INSERT_CREATE_ACTIVITY_STORY_CREATE =
            "INSERT INTO activity_stories " +
                    "(activity_id, update_date_time, time_spent, comment) " +
                    "SELECT a.id, request_date_time, ucar.total_time, comment " +
                    "FROM users_create_activity_requests ucar " +
                    "join activities a on ucar.category_id = a.category_id " +
                    "and ucar.user_id = a.user_id " +
                    "and ucar.title=a.title " +
                    "WHERE ucar.id = ?";
    public static final String DELETE_USER_REQUEST_NEW_ACTIVITY_FROM_CREATE_REQUESTS =
            "DELETE FROM " +
                    "users_create_activity_requests " +
                    "WHERE id = ?";
    public static final String INSERT_MESSAGE_DENY_CREATE_ACTIVITY_REQUEST =
            "INSERT INTO admin_comments " +
                    "(user_id, request_date_time, category_id, title, request_type_id, comment) " +
                    "SELECT user_id, request_date_time, category_id, title, ?, ? " + 
                    "FROM users_create_activity_requests  " +
                    "WHERE id = ?";


    public static final String DELETE_USER_ACTIVITY_BY_REQUEST_ID =
            "DELETE FROM activities " +
                    "WHERE id = (SELECT activity_id FROM users_delete_activity_requests WHERE id = ?)";
    public static final String DELETE_USER_REQUEST_DELETE_ACTIVITY_FROM_DELETE_REQUESTS =
            "DELETE FROM " +
                    "users_delete_activity_requests " +
                    "WHERE id = ?";
    public static final String INSERT_MESSAGE_DENY_DELETE_ACTIVITY_REQUEST =
            "INSERT INTO admin_comments " +
                    "(user_id, request_date_time, category_id, title, request_type_id, comment) " +
                    "SELECT udar.user_id, request_date_time, category_id, title, ?, ? " +
                    "FROM users_delete_activity_requests udar JOIN activities a on udar.activity_id = a.id " +
                    "WHERE udar.id = ?";


    public static final String SELECT_TOTAL_TIME_FROM_ACTIVITIES_BY_REQUEST =
            "SELECT total_time " +
                    "FROM activities " +
                    "WHERE id in (SELECT activity_id FROM users_append_time_activity_requests WHERE id = ?)";
    public static final String SELECT_APPEND_TIME_FROM_REQUESTS_BY_ID =
            "SELECT append_time " +
                    "FROM users_append_time_activity_requests " +
                    "WHERE id = ?";
    public static final String APPEND_TIME_ACTIVITY =
            "UPDATE activities " +
                    "SET total_time = ?, status_id = 2, last_update_date_time = " +
                    "(SELECT request_date_time FROM users_append_time_activity_requests WHERE id = ?) " +
                    "WHERE id = (SELECT activity_id FROM users_append_time_activity_requests WHERE id = ?)";
    public static final String INSERT_CREATE_ACTIVITY_STORY_APPEND_TIME =
            "INSERT INTO activity_stories " +
                    "(activity_id, update_date_time, time_spent, comment) " +
                    "SELECT activity_id, request_date_time, requests.append_time, comment " +
                    "FROM users_append_time_activity_requests requests " +
                    "WHERE requests.id = ?";
    public static final String DELETE_USER_REQUEST_APPEND_TIME_ACTIVITY_FROM_UPDATE_REQUESTS =
            "DELETE FROM " +
                    "users_append_time_activity_requests " +
                    "WHERE id = ?";
    public static final String INSERT_MESSAGE_DENY_APPEND_TIME_ACTIVITY_REQUEST =
            "INSERT INTO admin_comments " +
                    "(user_id, request_date_time, category_id, title,request_type_id, comment) " +
                    "SELECT uatar.user_id, request_date_time, category_id, title, ?, ? " +
                    " FROM users_append_time_activity_requests uatar JOIN activities a on a.id = uatar.activity_id " +
                    "WHERE uatar.id = ?";

    public static final String SELECT_USERS =
            "SELECT users.id, name, login, role " +
                    "FROM users " +
                    "JOIN roles on role_id = roles.id ";
    public static final String SELECT_ROLES = "SELECT id, role FROM roles";

    public static final String SELECT_DENIED_REQUESTS =
            "SELECT request_date_time, type as request_type, category, title, comment " +
            "FROM admin_comments " +
            "JOIN categories c on c.id = admin_comments.category_id " +
            "JOIN request_types rt on rt.id = admin_comments.request_type_id " +
            "WHERE user_id = ?";

    public static final String INSERT_ACTIVITY =  "INSERT INTO activities " +
            "(user_id, category_id, title, description, creation_date_time, last_update_date_time, total_time, status_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_LAST_VALUE_ACTIVITY_ID =
            "SELECT last_value FROM activities_id_seq";
    public static final String INSERT_ACTIVITY_STORY_ADD =
            "INSERT INTO activity_stories " +
                    "(activity_id, update_date_time, time_spent, comment) " +
                    "VALUES (?, ?, ?, ?)";

    public static final String INSERT_CATEGORY = "INSERT INTO categories (category) " +
            "VALUES (?)";
    public static final String INSERT_USER = "INSERT INTO users (name, login, password, role_id) " +
            "VALUES (?, ?, ?, ?)";

    public static final String DELETE_ACTIVITY_BY_ID = "DELETE FROM activities WHERE id = ?";
    public static final String DELETE_CATEGORY_BY_ID = "DELETE FROM categories WHERE id = ?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

}