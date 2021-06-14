package com.epam.timetracking.controller.util;

import com.epam.timetracking.pojo.entity.ActivityStatus;
import com.epam.timetracking.pojo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SorterTest {

    @Test
    public void testSorter() {
        Sorter<ActivityStatus> sorter = new Sorter<>(ActivityStatus.class);
        List<ActivityStatus> statuses = new ArrayList<>();
        statuses.add(new ActivityStatus(0, "new"));
        statuses.add(new ActivityStatus(1, "new"));
        statuses.add(new ActivityStatus(2, "deleted"));
        statuses.add(new ActivityStatus(3, "new"));
        statuses.add(new ActivityStatus(4, "on update"));
        statuses.add(new ActivityStatus(5, "changed"));

        List<ActivityStatus> statusesActual = sorter
                .sort(statuses, "status");

        List<ActivityStatus> statusesExpected = new ArrayList<>();
        statusesExpected.add(new ActivityStatus(5, "changed"));
        statusesExpected.add(new ActivityStatus(2, "deleted"));
        statusesExpected.add(new ActivityStatus(0, "new"));
        statusesExpected.add(new ActivityStatus(1, "new"));
        statusesExpected.add(new ActivityStatus(3, "new"));
        statusesExpected.add(new ActivityStatus(4, "on update"));

        Assertions.assertArrayEquals(statusesExpected.toArray(), statusesActual.toArray());
    }

    @Test
    public void testGetConditions(){
        List<String> conditionsActual = Sorter.getConditions(User.class);
        System.out.println(conditionsActual);
        List<String> conditionsExpected = List.of("activities", "id", "login", "name",  "password", "role", "roleId");
        Assertions.assertArrayEquals(conditionsExpected.toArray(), conditionsActual.toArray());
    }
}
