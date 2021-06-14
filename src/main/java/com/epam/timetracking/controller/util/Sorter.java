package com.epam.timetracking.controller.util;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sorter.
 * Type parameter:
 * <T> – the type of objects to be sorted
 */
@RequiredArgsConstructor
public class Sorter<T> {
    private final Class<T> resultClass;
    private static final Logger log = LogManager.getLogger(com.epam.timetracking.controller.util.SortComparator.class);

    /**
     * Sorts this list according to the order induced by the specified {@link SortComparator}
     *
     * @param list               - the list of objects to sort.
     * @param conditionFieldName - the name of the field that is the sorting condition.
     * @return - the sorted list.
     */
    public List<T> sort(List<T> list, String conditionFieldName) {
        list.sort(new SortComparator<>(resultClass, conditionFieldName));
        return list;
    }

    /**
     * Get conditions for sorting by class fields.
     *
     * @param clazz - the type of objects to be sorted.
     * @return - list of sorting conditions by object field names.
     */
    public static List<String> getConditions(Class<?> clazz) {
        return Arrays
                .stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .filter(fieldName -> !fieldName.equals("serialVersionUID"))
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

}
