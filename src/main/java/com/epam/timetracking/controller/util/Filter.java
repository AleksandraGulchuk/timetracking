package com.epam.timetracking.controller.util;

import com.epam.timetracking.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Filter<T> {

    private final Class<T> resultClass;
    private static final Logger log = LogManager.getLogger(Filter.class);

    public List<T> filter(List<T> list, String condition, String[] values) throws ServiceException {
        Method getCondition = getMethodCondition(condition);
        List<T> result = new ArrayList<>();
        List<T> tmp;
        for (String value : values) {
            tmp = list.stream().filter(t -> {
                try {
                    return getCondition.invoke(t).equals(value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Cannot invoke method: " + getCondition + ". " + e.getMessage(), e);
                }
                return false;
            }).collect(Collectors.toList());
            result.addAll(tmp);
        }
        return result;
    }

    private Method getMethodCondition(String condition) throws ServiceException {
        String methodName = "get" + condition.substring(0, 1).toUpperCase() + condition.substring(1);
        Method[] methods = resultClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new ServiceException("Must contain method getValue: " + methodName);
    }


}
