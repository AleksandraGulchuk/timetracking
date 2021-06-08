package com.epam.timetracking.dto;

import com.epam.timetracking.entities.Time;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Adapter<I, O> {
    private final Class<I> inputClass;
    private final Class<O> outputClass;
    private static final Logger log = LogManager.getLogger(Adapter.class);

    public O adapt(I inputObject) {
        try {
            O outputObject = outputClass.getConstructor().newInstance();
            for (Field outputField : outputClass.getDeclaredFields()) {
                outputField.setAccessible(true);
                if (!outputField.getName().equals("serialVersionUID")) {
                    log.debug("field name: " + outputField.getName());
                    outputField.set(outputObject, extractValue(inputObject, outputField));
                }
            }
            return outputObject;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Class must contain constructor without parameters!" + e.getMessage(), e);
            throw new RuntimeException("Class must contain constructor without parameters!", e);
        }
    }

    public List<O> adaptList(List<I> objects) {
        return objects.stream()
                .map(this::adapt)
                .collect(Collectors.toList());
    }


    private Object extractValue(I inputObject, Field outputField) throws IllegalAccessException {
        for (Field inputField : inputClass.getDeclaredFields()) {
            outputField.setAccessible(true);
            if (inputField.getName().equals(outputField.getName())) {
                inputField.setAccessible(true);
                if (inputField.getType().equals(Time.class)) {
                    return getLongValue(inputField, inputObject);
                }
                if (inputField.getType().equals(Long.class)) {
                    return getTimeValue(inputField, inputObject);
                }
//                if (inputField.getType().equals(List.class)) {
//                    return getListValue(inputField, inputObject);
//                }
                return inputField.get(inputObject);
            }
        }
        return null;
    }

//    private List<?> getListValue(Field inputField, I inputObject) throws IllegalAccessException, ClassNotFoundException {
//        List<?> list = (List<?>) inputField.get(inputObject);
//        if(list.isEmpty()){
//            return Collections.emptyList();
//        }
//        Class<?> inputClass = list.get(0).getClass();
//        String inputClassName = inputClass.getName();
//        if(inputClassName.contains("DTO")){
//            String outputClassName = inputClassName.substring(inputClassName.length()-3);
//            Adapter<> adapter = new Adapter<>(inputClass, Class.forName(outputClassName));
//            return
//
//                    new Adapter<>(inputClass, Class.forName(outputClassName)).adaptUNList(list);
//
//        } else {
//            return new Adapter<>(inputClass, Class.forName(inputClassName + "DTO")).adaptUNList(list);
//        }
//    }

    private Long getLongValue(Field inputField, I inputObject) throws IllegalAccessException {
        Time time = (Time) inputField.get(inputObject);
        if (time == null) {
            return 0L;
        }
        return time.getTime();
    }

    private Time getTimeValue(Field inputField, I inputObject) throws IllegalAccessException {
        Long longTime = (Long) inputField.get(inputObject);
        if (longTime == null) {
            return new Time();
        }
        return new Time(longTime);
    }


}
