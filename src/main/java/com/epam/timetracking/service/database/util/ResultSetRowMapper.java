package com.epam.timetracking.service.database.util;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ResultSetRowMapper<T> implements RowMapper<T> {
    private final Class<T> resultClass;
    private static final Logger log = LogManager.getLogger(ResultSetRowMapper.class);

    @Override
    public T map(ResultSet resultSet) {
        try {
            T object = resultClass.getConstructor().newInstance();
            for (Field field : resultClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equals("serialVersionUID")) {
                    log.debug("field name: " + field.getName());
                    field.set(object, extractValue(resultSet, field));
                }
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Class must contain constructor without parameters!" + e.getMessage(), e);
            throw new RuntimeException("Class must contain constructor without parameters!", e);
        }
    }

    private Object extractValue(ResultSet resultSet, Field field) {
        try {
            String fieldName = field.getName();
            if (!containsColumn(resultSet, fieldName)) {
                fieldName = toSnakeCase(fieldName);
                if (!containsColumn(resultSet, fieldName)) {
                    return null;
                }
            }
            return resultSet.getObject(fieldName, field.getType());
        } catch (SQLException e) {
            log.error("Cannot extract value from result set by field!" + e.getMessage(), e);
            throw new RuntimeException("Cannot extract value from result set by field!", e);
        }
    }

    private boolean containsColumn(ResultSet resultSet, String fieldName) throws SQLException {
        int colCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            if (resultSet.getMetaData().getColumnName(i).equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    private String toSnakeCase(String fieldName) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(fieldName);
        String result = fieldName;
        while (matcher.find()) {
            String replace = matcher.group();
            result = result.replaceAll(replace, "_" + replace.toLowerCase(Locale.ROOT));
        }
        return result;
    }
}
