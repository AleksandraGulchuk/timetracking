package com.epam.timetracking.service.database.util;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Template for queries to the database.
 */
@RequiredArgsConstructor
public class JdbcTemplate {
    private static final Logger log = LogManager.getLogger(JdbcTemplate.class);
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    /**
     * Executes query with parameters that returns results list.
     *
     * @param connection - connection to database.
     * @param sql        - an SQL statement that may contain one or more '?' IN parameter placeholders.
     * @param params     - array of parameters for SQL statement.
     * @param mapper     - an object mapper from result set.
     * @return - a list of objects returned from the query.
     */
    public <T> List<T> query(Connection connection, String sql, Object[] params, RowMapper<T> mapper) throws SQLException {
        List<T> results = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T o = mapper.map(resultSet);
                results.add(o);
            }
        } finally {
            closeResources(resultSet, preparedStatement);
        }
        return results;
    }

    /**
     * Executes query without parameters that returns results list.
     *
     * @param connection - connection to database.
     * @param sql        - an SQL statement that does not contain any '?' IN parameter placeholders.
     * @param mapper     - an object mapper from result set.
     * @return - a list of objects returned from the query.
     */
    public <T> List<T> query(Connection connection, String sql, RowMapper<T> mapper) throws SQLException {
        return query(connection, sql, new Object[]{}, mapper);
    }

    /**
     * Executes a query with parameters that returns only one result.
     *
     * @param connection - connection to database.
     * @param sql        - an SQL statement that may contain one or more '?' IN parameter placeholders.
     * @param params     - array of parameters for SQL statement.
     * @param mapper     - an object mapper from result set.
     * @return - object returned from the query.
     */
    public <T> T queryOne(Connection connection, String sql, Object[] params, RowMapper<T> mapper) throws SQLException {
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapper.map(resultSet);
            }
        } finally {
            closeResources(resultSet, preparedStatement);
        }
        return null;
    }

    /**
     * Executes a query without parameters that returns only one result.
     *
     * @param connection - connection to database.
     * @param sql        - an SQL statement that does not contain any '?' IN parameter placeholders.
     * @param mapper     - an object mapper from result set.
     * @return - object returned from the query.
     */
    public <T> T queryOne(Connection connection, String sql, RowMapper<T> mapper) throws SQLException {
        return queryOne(connection, sql, new Object[]{}, mapper);
    }

    /**
     * Executes a query with parameters that update the database without returning any result.
     *
     * @param connection - connection to database.
     * @param sql        - an SQL statement that may contain one or more '?' IN parameter placeholders.
     * @param params     - array of parameters for SQL statement.
     */
    public void update(Connection connection, String sql, Object[] params) throws SQLException {
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            int result = preparedStatement.executeUpdate();
            log.debug("Quantity of updated rows is: " + result);
        } finally {
            closeResources(preparedStatement);
        }
    }

    /**
     * Executes a query without parameters that update the database without returning any result.
     *
     * @param connection - connection to database.
     * @param sql        - an SQL statement that does not contain any '?' IN parameter placeholders.
     */
    public void update(Connection connection, String sql) throws SQLException {
        update(connection, sql, new Object[]{});
    }

    /**
     * Closes autocloseable resourses.
     *
     * @param resources - resources for database connection that implements {@link AutoCloseable}.
     */
    public static void closeResources(AutoCloseable... resources) {
        try {
            for (AutoCloseable resource : resources) {
                if (resource != null) {
                    resource.close();
                }
            }
        } catch (Exception exception) {
            log.error("Cannot close resources: " + exception.getMessage(), exception);
        }
    }
}
