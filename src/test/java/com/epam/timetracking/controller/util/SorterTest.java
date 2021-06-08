package com.epam.timetracking.controller.util;

import com.epam.timetracking.entities.ActivityStatus;
import com.epam.timetracking.exceptions.ServiceException;
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

        Assertions.assertEquals(statusesExpected.toString(), statusesActual.toString());
    }
}
