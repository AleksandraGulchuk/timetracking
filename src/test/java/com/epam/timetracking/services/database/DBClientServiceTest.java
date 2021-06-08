package com.epam.timetracking.services.database;

import com.epam.timetracking.entities.Activity;
import com.epam.timetracking.entities.ActivityStatus;
import com.epam.timetracking.entities.Category;
import com.epam.timetracking.entities.User;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.database.util.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBClientServiceTest {

//    private static JdbcTemplate jdbcTemplate;
//    private static DBClientService clientService;
//
//    @BeforeAll
//    public static void beforeTest() throws SQLException {
//        Connection connection = LocalDBConfig.getInstance().getDataSource().getConnection();
//        jdbcTemplate = new JdbcTemplate();
//        clientService = new DBClientService(LocalDBConfig.getInstance());
//        jdbcTemplate.update(connection, SQLQueries.SQL_INIT);
//        JdbcTemplate.closeResources(connection);
//    }
//
//    @Test
//    public void testGetUser() throws ServiceException {
//        User userActual = clientService.getUser("admin", "1");
//        User userExpected = new User();
//        userExpected.setLogin("admin");
//        userExpected.setName("Darth Vader");
//        userExpected.setId(1);
//        userExpected.setRole("admin");
//        Assertions.assertEquals(userExpected, userActual);
//    }
//
//    @Test
//    public void testGetCategories() throws ServiceException {
//        List<Category> categories = clientService.getCategories();
//        Assertions.assertNotNull(categories);
//    }
//
//    @Test
//    public void testGetActivities() throws ServiceException {
//        List<Activity> activities = clientService.getActivities(2);
//        Assertions.assertNotNull(activities);
//    }
//
//    @Test
//    public void testGetActivityStatuses() throws ServiceException {
//        List<ActivityStatus> statusesActual = clientService.getActivityStatuses();
//        List<ActivityStatus> statusesExpected = new ArrayList<>();
//        statusesExpected.add(new ActivityStatus(1, "new"));
//        statusesExpected.add(new ActivityStatus(2, "changed"));
//        statusesExpected.add(new ActivityStatus(3, "on update"));
//        statusesExpected.add(new ActivityStatus(4, "deleted"));
//        Assertions.assertArrayEquals(statusesExpected.toArray(), statusesActual.toArray());
//    }
//
//
//    @AfterAll
//    public static void afterTest() throws SQLException {
//        Connection connection = LocalDBConfig.getInstance().getDataSource().getConnection();
//        jdbcTemplate.update(connection, SQLQueries.SQL_DROP);
//        JdbcTemplate.closeResources(connection);
//    }

}
