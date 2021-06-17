package com.epam.timetracking.service.database;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.bean.ActivityDTO;
import com.epam.timetracking.pojo.bean.DeniedRequest;
import com.epam.timetracking.pojo.entity.*;
import com.epam.timetracking.service.ClientService;
import com.epam.timetracking.service.database.util.DBConfig;
import com.epam.timetracking.service.database.util.SQLQueries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

class DBClientServiceTest {

    private static ClientService clientService;
    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = Mockito.mock(Connection.class);
        DataSource dataSource = Mockito.mock(DataSource.class);
        DBConfig dbConfig = Mockito.mock(DBConfig.class);
        Mockito.when(dbConfig.getDataSource()).thenReturn(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        clientService = new DBClientService(dbConfig);
    }

    @Test
    public void testGetCategories() throws ServiceException, SQLException {
        List<Category> categoriesExpect = List.of(new Category(1, "project"), new Category(2, "meeting"));
        configConnectionForGetCategories();
        List<Category> categoriesActual = clientService.getCategories();
        Assertions.assertArrayEquals(categoriesExpect.toArray(), categoriesActual.toArray());
    }

    private void configConnectionForGetCategories() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(2);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("category");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(1).thenReturn(2);
        Mockito.when(resultSet.getObject("category", String.class)).thenReturn("project").thenReturn("meeting");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_CATEGORIES)).thenReturn(preparedStatement);
    }

    @Test
    void testGetActivities() throws ServiceException, SQLException {
        List<Activity> activitiesExpect = List.of(
                new Activity(1, "project", null, "title1", "description1",
                        LocalDateTime.of(2021, 6, 17, 12, 10),
                        LocalDateTime.of(2021, 6, 17, 12, 10),
                        null, 0L, "new", null, null, null),
                new Activity(2, "meeting", null, "title2", "description2",
                        LocalDateTime.of(2021, 6, 17, 12, 20),
                        LocalDateTime.of(2021, 6, 17, 12, 20),
                        null, 0L, "new", null, null, null)
        );
        configConnectionForGetActivities();
        List<Activity> activitiesActual = clientService.getActivities(2);
        Assertions.assertArrayEquals(activitiesExpect.toArray(), activitiesActual.toArray());
    }

    private void configConnectionForGetActivities() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(8);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("category");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("title");
        Mockito.when(resultSetMetaData.getColumnName(4)).thenReturn("description");
        Mockito.when(resultSetMetaData.getColumnName(5)).thenReturn("creation_date_time");
        Mockito.when(resultSetMetaData.getColumnName(6)).thenReturn("last_update_date_time");
        Mockito.when(resultSetMetaData.getColumnName(7)).thenReturn("total_time");
        Mockito.when(resultSetMetaData.getColumnName(8)).thenReturn("status");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(1).thenReturn(2);
        Mockito.when(resultSet.getObject("category", String.class)).thenReturn("project").thenReturn("meeting");
        Mockito.when(resultSet.getObject("title", String.class)).thenReturn("title1").thenReturn("title2");
        Mockito.when(resultSet.getObject("description", String.class)).thenReturn("description1").thenReturn("description2");

        Mockito.when(resultSet.getObject("creation_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 10))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 20));

        Mockito.when(resultSet.getObject("last_update_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 10))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 20));

        Mockito.when(resultSet.getObject("total_time", Long.class)).thenReturn(0L).thenReturn(0L);
        Mockito.when(resultSet.getObject("status", String.class)).thenReturn("new").thenReturn("new");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_ACTIVITIES_BY_USER_ID)).thenReturn(preparedStatement);
    }

    @Test
    public void testGetActivity() throws ServiceException, SQLException {
        Activity activityExpect = new Activity(1, "project", 1, "title1", "description1",
                LocalDateTime.of(2021, 6, 17, 12, 10),
                LocalDateTime.of(2021, 6, 17, 12, 10),
                null, 0L, "new", null, null,
                List.of(new ActivityStory(null, null,
                        LocalDateTime.of(2021, 6, 17, 12, 10), 0L, "new")
                ));

        configConnectionForGetActivity();
        Activity activityActual = clientService.getActivity(2);
        Assertions.assertEquals(activityExpect, activityActual);
    }

    private void configConnectionForGetActivity() throws SQLException {
        PreparedStatement preparedStatement1 = getPreparedStatement1();
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_AN_ACTIVITY_BY_ID)).thenReturn(preparedStatement1);

        PreparedStatement preparedStatement2 = getPreparedStatement2();
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_ACTIVITY_STORIES_BY_ACTIVITY_ID)).thenReturn(preparedStatement2);
    }

    private PreparedStatement getPreparedStatement1() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(9);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("category_id");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("category");
        Mockito.when(resultSetMetaData.getColumnName(4)).thenReturn("title");
        Mockito.when(resultSetMetaData.getColumnName(5)).thenReturn("description");
        Mockito.when(resultSetMetaData.getColumnName(6)).thenReturn("creation_date_time");
        Mockito.when(resultSetMetaData.getColumnName(7)).thenReturn("last_update_date_time");
        Mockito.when(resultSetMetaData.getColumnName(8)).thenReturn("total_time");
        Mockito.when(resultSetMetaData.getColumnName(9)).thenReturn("status");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(1);
        Mockito.when(resultSet.getObject("category_id", Integer.class)).thenReturn(1);
        Mockito.when(resultSet.getObject("category", String.class)).thenReturn("project");
        Mockito.when(resultSet.getObject("title", String.class)).thenReturn("title1");
        Mockito.when(resultSet.getObject("description", String.class)).thenReturn("description1");

        Mockito.when(resultSet.getObject("creation_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 10));

        Mockito.when(resultSet.getObject("last_update_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 10));

        Mockito.when(resultSet.getObject("total_time", Long.class)).thenReturn(0L);
        Mockito.when(resultSet.getObject("status", String.class)).thenReturn("new");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        return preparedStatement;
    }

    private PreparedStatement getPreparedStatement2() throws SQLException {

        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);

        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(3);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("update_date_time");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("time_spent");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("comment");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("update_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 10));
        Mockito.when(resultSet.getObject("time_spent", Long.class)).thenReturn(0L);
        Mockito.when(resultSet.getObject("comment", String.class)).thenReturn("new");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        return preparedStatement;
    }

    @Test
    void testGetActivityStatuses() throws ServiceException, SQLException {
        List<ActivityStatus> statusesExpect = List.of(
                new ActivityStatus(1, "new"), new ActivityStatus(2, "changed"));
        configConnectionForGetActivityStatuses();
        List<ActivityStatus> statusesActual = clientService.getActivityStatuses();
        Assertions.assertArrayEquals(statusesExpect.toArray(), statusesActual.toArray());
    }

    private void configConnectionForGetActivityStatuses() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(2);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("status");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(1).thenReturn(2);
        Mockito.when(resultSet.getObject("status", String.class)).thenReturn("new").thenReturn("changed");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_ACTIVITY_STATUSES)).thenReturn(preparedStatement);
    }

    @Test
    void testGetUser() throws ServiceException, SQLException {
        List<ActivityDTO> activityDTOList = new Adapter<Activity, ActivityDTO>(Activity.class, ActivityDTO.class)
                .adaptList(List.of(
                        new Activity(1, "project", null, "title1", "description1",
                                LocalDateTime.of(2021, 6, 17, 12, 10),
                                LocalDateTime.of(2021, 6, 17, 12, 10),
                                null, 0L, "new", null, null, null),
                        new Activity(2, "meeting", null, "title2", "description2",
                                LocalDateTime.of(2021, 6, 17, 12, 20),
                                LocalDateTime.of(2021, 6, 17, 12, 20),
                                null, 0L, "new", null, null, null)
                ));
        User userExpect = new User(2, "name", "client", null, "client", null,
                activityDTOList);
        configConnectionForGetUser();
        configConnectionForGetActivities();
        User userActual = clientService.getUser("client", "2");
        Assertions.assertEquals(userExpect, userActual);
    }

    private void configConnectionForGetUser() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(4);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("name");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("login");
        Mockito.when(resultSetMetaData.getColumnName(4)).thenReturn("role");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("id", Integer.class)).thenReturn(2);
        Mockito.when(resultSet.getObject("name", String.class)).thenReturn("name");
        Mockito.when(resultSet.getObject("login", String.class)).thenReturn("client");
        Mockito.when(resultSet.getObject("role", String.class)).thenReturn("client");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_A_USER_BY_LOGIN_AND_PASSWORD)).thenReturn(preparedStatement);
    }

    @Test
    void testGetDeniedRequests() throws ServiceException, SQLException {
        List<DeniedRequest> deniedRequestsExpect = List.of(
                new DeniedRequest(null, LocalDateTime.of(2021, 6, 17, 12, 10),
                        "delete", "project", "title1", "comment1"),
                new DeniedRequest(null, LocalDateTime.of(2021, 6, 17, 12, 20),
                        "create NEW", "project", "title2", "comment2")
        );
        configConnectionForGetDeniedRequests();
        List<DeniedRequest> deniedRequestsActual = clientService.getDeniedRequests(2);
        Assertions.assertArrayEquals(deniedRequestsExpect.toArray(), deniedRequestsActual.toArray());
    }

    private void configConnectionForGetDeniedRequests() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(5);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("request_date_time");
        Mockito.when(resultSetMetaData.getColumnName(2)).thenReturn("request_type");
        Mockito.when(resultSetMetaData.getColumnName(3)).thenReturn("category");
        Mockito.when(resultSetMetaData.getColumnName(4)).thenReturn("title");
        Mockito.when(resultSetMetaData.getColumnName(5)).thenReturn("comment");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject("request_date_time", LocalDateTime.class))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 10))
                .thenReturn(LocalDateTime.of(2021, 6, 17, 12, 20));
        Mockito.when(resultSet.getObject("request_type", String.class)).thenReturn("delete").thenReturn("create NEW");
        Mockito.when(resultSet.getObject("category", String.class)).thenReturn("project").thenReturn("project");
        Mockito.when(resultSet.getObject("title", String.class)).thenReturn("title1").thenReturn("title2");
        Mockito.when(resultSet.getObject("comment", String.class)).thenReturn("comment1").thenReturn("comment2");

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_DENIED_REQUESTS)).thenReturn(preparedStatement);
    }

}