package com.epam.timetracking.dto;

import com.epam.timetracking.entities.ActivityStory;
import com.epam.timetracking.entities.Time;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class AdapterTest {

    @Test
    public void testAdaptEntity(){
        Adapter<ActivityStory, ActivityStoryDTO> adapter = new Adapter<>(ActivityStory.class, ActivityStoryDTO.class);
        LocalDateTime dateTime = LocalDateTime.now();
        ActivityStory activityStory = new ActivityStory(1,2, dateTime, 3600L, "test");
        ActivityStoryDTO activityStoryDTOActual = adapter.adapt(activityStory);
        ActivityStoryDTO activityStoryDTOExpect = new ActivityStoryDTO(1,2,dateTime, new Time(3600L), "test");
        Assertions.assertEquals(activityStoryDTOActual, activityStoryDTOExpect);
    }

    @Test
    public void testAdaptDTO(){
        Adapter<ActivityStoryDTO, ActivityStory> adapter = new Adapter<>(ActivityStoryDTO.class, ActivityStory.class);
        LocalDateTime dateTime = LocalDateTime.now();
        ActivityStoryDTO activityStoryDTO = new ActivityStoryDTO(1,2, dateTime, new Time(3600L), "test");
        ActivityStory activityStoryActual = adapter.adapt(activityStoryDTO);
        ActivityStory activityStoryExpect = new ActivityStory(1,2,dateTime, 3600L, "test");
        Assertions.assertEquals(activityStoryActual, activityStoryExpect);
    }
}
