package com.epam.timetracking.services.database;

import com.epam.timetracking.entities.UserRequest;
import com.epam.timetracking.entities.Activity;
import com.epam.timetracking.entities.Role;
import com.epam.timetracking.entities.User;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.database.util.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBAdminServiceTest {

//    private static JdbcTemplate jdbcTemplate;
//    private static DBAdminService adminService;
//    private static DBClientService clientService;
//
//    @BeforeAll
//    public static void beforeTest() throws SQLException {
//        Connection connection = LocalDBConfig.getInstance().getDataSource().getConnection();
//        jdbcTemplate = new JdbcTemplate();
//        adminService = new DBAdminService(LocalDBConfig.getInstance());
//        clientService = new DBClientService(LocalDBConfig.getInstance());
//        jdbcTemplate.update(connection, SQLQueries.SQL_INIT);
//        JdbcTemplate.closeResources(connection);
//    }
//
//    @Test
//    public void testGetUsers() throws ServiceException {
//        List<User> users = adminService.getUsers();
//        Assertions.assertNotNull(users);
//    }
//
//    @Test
//    public void testGetActivities() throws ServiceException {
//        List<Activity> activities = adminService.getActivities();
//        Assertions.assertNotNull(activities);
//    }
//
//    @Test
//    public void testGetRoles() throws ServiceException {
//        List<Role> rolesActual = adminService.getRoles();
//        Role[] rolesExpected = new Role[]{new Role(1, "admin"), new Role(2, "client")};
//        Assertions.assertArrayEquals(rolesExpected, rolesActual.toArray());
//    }
//
//    @Test
//    public void testGetUserRequests() throws ServiceException {
//        List<UserRequest> requests = adminService.getUserRequests();
//        Assertions.assertNotNull(requests);
//    }
//
//    @Test
//    public void testAddCategory() throws ServiceException {
////        Category category = new Category();
////        category.setCategory("test");
////        adminService.addCategory(category);
////        List<Category> categories = clientService.getCategories();
////        Assertions.assertTrue(categories.stream().map(Category::getCategory).anyMatch(c -> c.equals("test")));
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
