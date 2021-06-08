package com.epam.timetracking.controller.util;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Enumeration;

@RequiredArgsConstructor
public class RequestMapper<T> implements Mapper<T> {
    private final Class<T> resultClass;
    private static final Logger log = LogManager.getLogger(RequestMapper.class);

    @Override
    public T map(HttpServletRequest req) {
        try {
            T object = resultClass.getConstructor().newInstance();
            for (Field field : resultClass.getDeclaredFields()) {
                field.setAccessible(true);
                String parameter = extractValue(req, field);
                setParameter(object, field, parameter);
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Class must contain constructor without parameters!" + e.getMessage(), e);
            throw new RuntimeException("Class must contain constructor without parameters!", e);
        }
    }

    private String extractValue(HttpServletRequest req, Field field) {
        try {
            String fieldName = field.getName();
            if (!containsParameter(req, fieldName)) {
                return null;
            }
            return req.getParameter(fieldName);
        } catch (SQLException e) {
            log.error("Cannot extract value from result set by field!" + e.getMessage(), e);
            throw new RuntimeException("Cannot extract value from result set by field!", e);
        }
    }

    private boolean containsParameter(HttpServletRequest req, String fieldName) throws SQLException {
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            if (fieldName.equals(parameterNames.nextElement())) {
                return true;
            }
        }
        return false;
    }

    private void setParameter(T object, Field field, String parameter) throws IllegalAccessException, InvocationTargetException {
        if (field.getName().equals("serialVersionUID")) {
            return;
        }
        if (parameter == null || parameter.equals("")) {
            field.set(object, null);
            return;
        }
        Class<?> fieldClass = field.getType();
        if (fieldClass.equals(String.class)) {
            field.set(object, parameter);
            return;
        }
        Method[] methods = fieldClass.getMethods();
        for (Method method : methods) {
            if (method.getName().contains("parse")) {
                if (method.getParameterCount() > 1) {
                    continue;
                }
                field.set(object, (method.invoke(null, parameter)));
                return;
            }
        }
    }
}
