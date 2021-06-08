package com.epam.timetracking.controller.util;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Sorter<T> {
    private final Class<T> resultClass;
    private static final Logger log = LogManager.getLogger(com.epam.timetracking.controller.util.SortComparator.class);

    public List<T> sort(List<T> list, String conditionFieldName) {
        list.sort(new SortComparator<>(resultClass, conditionFieldName));
        return list;
    }

    public static List<String> getConditions(Class<?> clazz) {
        return Arrays
                .stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

}
