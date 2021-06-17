package com.epam.timetracking.service.database.util;

import com.epam.timetracking.pojo.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

class JdbcTemplateTest {

    private static JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void beforeAll() {
        jdbcTemplate = new JdbcTemplate();
    }

    @Test
    void testQuery() throws SQLException {
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

        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_ROLES)).thenReturn(preparedStatement);

        List<Role> rolesActual = jdbcTemplate.query(connection,
                SQLQueries.SELECT_ROLES, new ResultSetRowMapper<>(Role.class));
        Role[] rolesExpected = new Role[]{new Role(1, "admin"), new Role(2, "client")};
        Assertions.assertArrayEquals(rolesExpected, rolesActual.toArray());
    }

    @Test
    void testQueryOne() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true);

        Mockito.when(resultSet.getInt("id")).thenReturn(1);

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(connection.prepareStatement(SQLQueries.SELECT_STATUS_ID_BY_STATUS)).thenReturn(preparedStatement);

        Integer id = jdbcTemplate.queryOne(connection,
                SQLQueries.SELECT_STATUS_ID_BY_STATUS, new String[]{"new"},
                Fields.getIntegerRowMapper("id"));

        Assertions.assertEquals(1, id);
    }

}