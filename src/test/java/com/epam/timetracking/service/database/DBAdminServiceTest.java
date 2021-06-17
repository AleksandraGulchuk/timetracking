package com.epam.timetracking.service.database;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.*;
import com.epam.timetracking.service.AdminService;
import com.epam.timetracking.service.database.util.DBConfig;
import com.epam.timetracking.service.database.util.SQLQueries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

class DBAdminServiceTest {

    private static AdminService adminService;
    private static Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = Mockito.mock(Connection.class);
        DataSource dataSource = Mockito.mock(DataSource.class);
        DBConfig dbConfig = Mockito.mock(DBConfig.class);
        Mockito.when(dbConfig.getDataSource()).thenReturn(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        adminService = new DBAdminService(dbConfig);
    }

    @Test
    void testGetUsers() throws ServiceException, SQLException {
        List<User> usersExpect = List.of(
                new User(1, "name1", "admin", null, "admin", null, null),
                new User(2, "name2", "client", null, "client", null, null));
        configConnectionForGetUsers();
        List<User> usersActual = adminService.getUsers();
        Assertions.assertArrayEquals(usersExpect.toArray(), usersActual.toArray());
    }

    private void configConnectionForGetUsers() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(4);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("name");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("login");
        Mockito.when(resultSetMetaData.getColumnName(4)).thenReturn("role");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(1).thenReturn(2);
        Mockito.when(resultSet.getObject("name", String.class)).thenReturn("name1").thenReturn("name2");
        Mockito.when(resultSet.getObject("login", String.class)).thenReturn("admin").thenReturn("client");
        Mockito.when(resultSet.getObject("role", String.class)).thenReturn("admin").thenReturn("client");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_USERS)).thenReturn(preparedStatement);
    }

    @Test
    void testGetRoles() throws ServiceException, SQLException {
        List<Role> rolesExpect = List.of(new Role(1, "admin"), new Role(2, "client"));
        configConnectionForGetRoles();
        List<Role> rolesActual = adminService.getRoles();
        Assertions.assertArrayEquals(rolesExpect.toArray(), rolesActual.toArray());
    }

    private void configConnectionForGetRoles() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(2);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("role");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(1).thenReturn(2);
        Mockito.when(resultSet.getObject("role", String.class)).thenReturn("admin").thenReturn("client");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_ROLES)).thenReturn(preparedStatement);
    }

    @Test
    void testGetActivities() throws ServiceException, SQLException {
        List<ActivityStory> activityStories = List.of(
                new ActivityStory(null, null,
                        LocalDateTime.of(2021, 6, 17, 12, 10), 0L, "comment 1"),
                new ActivityStory(null, null,
                        LocalDateTime.of(2021, 6, 17, 12, 20), 3600L, "comment 2")
        );

        List<Activity> activitiesExpect = List.of(
                new Activity(1, "project", null, "title1", "description1",
                        LocalDateTime.of(2021, 6, 17, 12, 10), null,
                        null, 0L, null, null, "user name 1", activityStories)
        );
        configConnectionForGetActivities();
        configConnectionForGetActivityStories();
        List<Activity> activitiesActual = adminService.getActivities();
        Assertions.assertArrayEquals(activitiesExpect.get(0).getStories().toArray(), activitiesActual.get(0).getStories().toArray());
    }

    private void configConnectionForGetActivities() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(6);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("category");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("title");
        Mockito.when(resultSetMetaData.getColumnName(4)).thenReturn("description");
        Mockito.when(resultSetMetaData.getColumnName(5)).thenReturn("creation_date_time");
        Mockito.when(resultSetMetaData.getColumnName(6)).thenReturn("user_name");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(1).thenReturn(2);
        Mockito.when(resultSet.getObject("category", String.class)).thenReturn("project");
        Mockito.when(resultSet.getObject("title", String.class)).thenReturn("title1");
        Mockito.when(resultSet.getObject("description", String.class)).thenReturn("description1");
        Mockito.when(resultSet.getObject("creation_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 10));
        Mockito.when(resultSet.getObject("user_name", String.class)).thenReturn("user name 1");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_ACTIVITIES)).thenReturn(preparedStatement);
    }

    private void configConnectionForGetActivityStories() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(3);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("update_date_time");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("time_spent");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("comment");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("update_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 10))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 20));
        Mockito.when(resultSet.getObject("time_spent", Long.class)).thenReturn(0L).thenReturn(3600L);
        Mockito.when(resultSet.getObject("comment", String.class)).thenReturn("comment 1").thenReturn("comment 2");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_ACTIVITY_STORIES_BY_ACTIVITY_ID)).thenReturn(preparedStatement);
    }

    @Test
    void testGetUserRequests() throws ServiceException, SQLException {
        List<UserRequest> userRequestsExpect = List.of(
                new UserRequest(1, LocalDateTime.of(2021, 6, 17, 12, 10), "name1",
                        1, "category1", "title1", "description1", 0L, "on update", "comment1", "delete"),
                new UserRequest(2, LocalDateTime.of(2021, 6, 17, 12, 20), "name2",
                        2, "category2", "title2", "description2", 3600L, "on update", "comment2", "delete"),
                new UserRequest(3, LocalDateTime.of(2021, 6, 17, 12, 30), "name3",
                        3, "category3", "title3", "description3", 0L, "on update", "comment3", "create NEW")
        );
        configConnectionForGetUserRequestsDelete();
        configConnectionForGetUserRequestsCreate();
        List<UserRequest> userRequestsActual = adminService.getUserRequests();
        Assertions.assertArrayEquals(userRequestsExpect.toArray(), userRequestsActual.toArray());
    }

    private void configConnectionForGetUserRequestsDelete() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(11);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("request_date_time");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("user_name");
        Mockito.when(resultSetMetaData.getColumnName(4)).thenReturn("activity_id");
        Mockito.when(resultSetMetaData.getColumnName(5)).thenReturn("category");
        Mockito.when(resultSetMetaData.getColumnName(6)).thenReturn("title");
        Mockito.when(resultSetMetaData.getColumnName(7)).thenReturn("description");
        Mockito.when(resultSetMetaData.getColumnName(8)).thenReturn("total_time");
        Mockito.when(resultSetMetaData.getColumnName(9)).thenReturn("status");
        Mockito.when(resultSetMetaData.getColumnName(10)).thenReturn("comment");
        Mockito.when(resultSetMetaData.getColumnName(11)).thenReturn("request_type");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(1).thenReturn(2);
        Mockito.when(resultSet.getObject("request_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 10))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 20));
        Mockito.when(resultSet.getObject("user_name", String.class)).thenReturn("name1").thenReturn("name2");
        Mockito.when(resultSet.getObject("activity_id", Integer.class)).thenReturn(1).thenReturn(2);
        Mockito.when(resultSet.getObject("category", String.class)).thenReturn("category1").thenReturn("category2");
        Mockito.when(resultSet.getObject("title", String.class)).thenReturn("title1").thenReturn("title2");
        Mockito.when(resultSet.getObject("description", String.class)).thenReturn("description1").thenReturn("description2");
        Mockito.when(resultSet.getObject("total_time", Long.class)).thenReturn(0L).thenReturn(3600L);
        Mockito.when(resultSet.getObject("status", String.class)).thenReturn("on update").thenReturn("on update");
        Mockito.when(resultSet.getObject("comment", String.class)).thenReturn("comment1").thenReturn("comment2");
        Mockito.when(resultSet.getObject("request_type", String.class)).thenReturn("delete").thenReturn("delete");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_USERS_DELETE_REQUESTS)).thenReturn(preparedStatement);
    }

    private void configConnectionForGetUserRequestsCreate() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(11);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("request_date_time");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("user_name");
        Mockito.when(resultSetMetaData.getColumnName(4)).thenReturn("activity_id");
        Mockito.when(resultSetMetaData.getColumnName(5)).thenReturn("category");
        Mockito.when(resultSetMetaData.getColumnName(6)).thenReturn("title");
        Mockito.when(resultSetMetaData.getColumnName(7)).thenReturn("description");
        Mockito.when(resultSetMetaData.getColumnName(8)).thenReturn("total_time");
        Mockito.when(resultSetMetaData.getColumnName(9)).thenReturn("status");
        Mockito.when(resultSetMetaData.getColumnName(10)).thenReturn("comment");
        Mockito.when(resultSetMetaData.getColumnName(11)).thenReturn("request_type");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(3);
        Mockito.when(resultSet.getObject("request_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 30));
        Mockito.when(resultSet.getObject("user_name", String.class)).thenReturn("name3");
        Mockito.when(resultSet.getObject("activity_id", Integer.class)).thenReturn(3);
        Mockito.when(resultSet.getObject("category", String.class)).thenReturn("category3");
        Mockito.when(resultSet.getObject("title", String.class)).thenReturn("title3");
        Mockito.when(resultSet.getObject("description", String.class)).thenReturn("description3");
        Mockito.when(resultSet.getObject("total_time", Long.class)).thenReturn(0L);
        Mockito.when(resultSet.getObject("status", String.class)).thenReturn("on update");
        Mockito.when(resultSet.getObject("comment", String.class)).thenReturn("comment3");
        Mockito.when(resultSet.getObject("request_type", String.class)).thenReturn("create NEW");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_USERS_CREATE_REQUESTS)).thenReturn(preparedStatement);
    }
}