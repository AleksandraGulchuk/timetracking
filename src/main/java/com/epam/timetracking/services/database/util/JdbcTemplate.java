package com.epam.timetracking.services.database.util;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JdbcTemplate {
    private static final Logger log = LogManager.getLogger(JdbcTemplate.class);
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

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

    public <T> List<T> query(Connection connection, String sql, RowMapper<T> mapper) throws SQLException {
        return query(connection, sql, new Object[]{}, mapper);
    }


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

    public <T> T queryOne(Connection connection, String sql, RowMapper<T> mapper) throws SQLException {
        return queryOne(connection, sql, new Object[]{}, mapper);
    }


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

    public void update(Connection connection, String sql) throws SQLException {
        update(connection, sql, new Object[]{});
    }

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
