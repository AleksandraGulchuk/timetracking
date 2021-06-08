package com.epam.timetracking.controller.util;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

@RequiredArgsConstructor
public class SortComparator<T> implements Comparator<T> {
    private static final Logger log = LogManager.getLogger(SortComparator.class);
    private final Class<T> resultClass;
    private final String conditionFieldName;

    @Override
    public int compare(T object1, T object2) {
        try {
            Object fieldValueObject1 = getObject(object1);
            Object fieldValueObject2 = getObject(object2);
            Field conditionField = getConditionField();
            if (conditionField == null) {
                return 0;
            }
            for (Method method : conditionField.getType().getMethods()) {
                if (method.getName().equals("compareTo")) {
                    if (fieldValueObject1 != null && fieldValueObject2 != null) {
                        return (int) method.invoke(fieldValueObject1, fieldValueObject2);
                    }
                    if (fieldValueObject1 == null){
                        return 1;
                    }
                    return -1;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    private Object getObject(T object1) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String getConditionFieldMethodName =
                "get" + conditionFieldName.substring(0, 1).toUpperCase() + conditionFieldName.substring(1);
        return resultClass
                        .getMethod(getConditionFieldMethodName)
                        .invoke(object1);
    }

    private Field getConditionField() {
        for (Field field : resultClass.getDeclaredFields()) {
            if (field.getName().equals(conditionFieldName)) {
                return field;
            }
        }
        return null;
    }
}
