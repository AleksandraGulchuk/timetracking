package com.epam.timetracking.controller.util;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.ActivityStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FilterTest {

    @Test
    public void testFilter() {
        Filter<ActivityStatus> filter = new Filter<>(ActivityStatus.class);
        List<ActivityStatus> statuses = new ArrayList<>();
        statuses.add(new ActivityStatus(0, "new"));
        statuses.add(new ActivityStatus(1, "new"));
        statuses.add(new ActivityStatus(2, "deleted"));
        statuses.add(new ActivityStatus(3, "new"));
        statuses.add(new ActivityStatus(4, "on update"));
        statuses.add(new ActivityStatus(5, "changed"));
        try {
            List<ActivityStatus> statusesActual = filter
                    .filter(statuses, "status", new String[]{"new", "changed"});
            ActivityStatus[] statusesExpected = new ActivityStatus[]{
                    new ActivityStatus(0, "new"),
                    new ActivityStatus(1, "new"),
                    new ActivityStatus(3, "new"),
                    new ActivityStatus(5, "changed")
            };
            Assertions.assertArrayEquals(statusesExpected, statusesActual.toArray());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
