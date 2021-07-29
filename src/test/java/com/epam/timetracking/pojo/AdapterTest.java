package com.epam.timetracking.pojo;

import com.epam.timetracking.pojo.dto.ActivityDTO;
import com.epam.timetracking.pojo.dto.ActivityStoryDTO;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.ActivityStory;
import com.epam.timetracking.pojo.entity.Time;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class AdapterTest {

    @Test
    public void testAdaptEntity() {
        Adapter<ActivityStory, ActivityStoryDTO> adapter = new Adapter<>(ActivityStory.class, ActivityStoryDTO.class);
        LocalDateTime dateTime = LocalDateTime.now();
        ActivityStory activityStory = new ActivityStory(1, 2, dateTime, 3600L, "test");
        ActivityStoryDTO activityStoryDTOActual = adapter.adapt(activityStory);
        ActivityStoryDTO activityStoryDTOExpect = new ActivityStoryDTO(1, 2, dateTime, new Time(3600L), "test");
        Assertions.assertEquals(activityStoryDTOActual, activityStoryDTOExpect);
    }

    @Test
    public void testAdaptDTO() {
        Adapter<ActivityStoryDTO, ActivityStory> adapter = new Adapter<>(ActivityStoryDTO.class, ActivityStory.class);
        LocalDateTime dateTime = LocalDateTime.now();
        ActivityStoryDTO activityStoryDTO = new ActivityStoryDTO(1, 2, dateTime, new Time(3600L), "test");
        ActivityStory activityStoryActual = adapter.adapt(activityStoryDTO);
        ActivityStory activityStoryExpect = new ActivityStory(1, 2, dateTime, 3600L, "test");
        Assertions.assertEquals(activityStoryActual, activityStoryExpect);
    }

    @Test
    public void testAdaptEntity_Null_Time() {
        Adapter<ActivityStory, ActivityStoryDTO> adapter = new Adapter<>(ActivityStory.class, ActivityStoryDTO.class);
        LocalDateTime dateTime = LocalDateTime.now();

        ActivityStory activityStory = new ActivityStory();
        activityStory.setId(1);
        activityStory.setActivityId(2);
        activityStory.setUpdateDateTime(dateTime);

        ActivityStoryDTO activityStoryDTOActual = adapter.adapt(activityStory);
        ActivityStoryDTO activityStoryDTOExpect = new ActivityStoryDTO(1, 2, dateTime, new Time(), null);
        Assertions.assertEquals(activityStoryDTOActual, activityStoryDTOExpect);
    }

    @Test
    public void testAdaptDTO_Null_Time() {
        Adapter<ActivityStoryDTO, ActivityStory> adapter = new Adapter<>(ActivityStoryDTO.class, ActivityStory.class);
        LocalDateTime dateTime = LocalDateTime.now();
        ActivityStoryDTO activityStoryDTO = new ActivityStoryDTO();
        activityStoryDTO.setId(1);
        activityStoryDTO.setActivityId(2);
        activityStoryDTO.setUpdateDateTime(dateTime);

        ActivityStory activityStoryActual = adapter.adapt(activityStoryDTO);
        ActivityStory activityStoryExpect = new ActivityStory(1, 2, dateTime, 0L, null);
        Assertions.assertEquals(activityStoryActual, activityStoryExpect);
    }

    @Test
    public void testAdaptList() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<ActivityDTO> activityDTOList = List.of(
                new ActivityDTO(1, "project", 1, "title1", "description1", dateTime, null, new Time(), null, "new", null, "userName1", null),
                new ActivityDTO(2, "project", 1, "title2", "description2", dateTime, null, new Time(), null, "new", null, "userName2", null),
                new ActivityDTO(3, "project", 1, "title3", "description3", dateTime, null, new Time(), null, "new", null, "userName3", null)
        );

        List<Activity> activityListExpected = List.of(
                new Activity(1, "project", 1, "title1", "description1", dateTime, null, 0L, 0L, "new", null, "userName1", null),
                new Activity(2, "project", 1, "title2", "description2", dateTime, null, 0L, 0L, "new", null, "userName2", null),
                new Activity(3, "project", 1, "title3", "description3", dateTime, null, 0L, 0L, "new", null, "userName3", null)
        );

        List<Activity> activityListActual = new Adapter<>(ActivityDTO.class, Activity.class).adaptList(activityDTOList);
        Assertions.assertArrayEquals(activityListExpected.toArray(), activityListActual.toArray());
    }
}
