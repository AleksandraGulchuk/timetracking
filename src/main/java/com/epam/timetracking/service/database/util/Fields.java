package com.epam.timetracking.service.database.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Holder for fields names of database tables.
 */
public class Fields {
    private static final Logger log = LogManager.getLogger(Fields.class);
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String TOTAL_TIME = "total_time";
    public static final String LAST_VALUE = "last_value";

    public static RowMapper<String> getStringRowMapper(String field) {
        return resultSet -> {
            try {
                return resultSet.getString(field);
            } catch (SQLException exception) {
                log.error("Cannot get result set by field - " + field + " : " + exception.getMessage(), exception);
            }
            return null;
        };
    }

    public static RowMapper<Integer> getIntegerRowMapper(String field) {
        return resultSet -> {
            try {
                return resultSet.getInt(field);
            } catch (SQLException exception) {
                log.error("Cannot get result set by field - " + field + " : " + exception.getMessage(), exception);
            }
            return null;
        };
    }

    public static RowMapper<Long> getLongRowMapper(String field) {
        return resultSet -> {
            try {
                return resultSet.getLong(field);
            } catch (SQLException exception) {
                log.error("Cannot get result set by field - " + field + " : " + exception.getMessage(), exception);
            }
            return null;
        };
    }

}
