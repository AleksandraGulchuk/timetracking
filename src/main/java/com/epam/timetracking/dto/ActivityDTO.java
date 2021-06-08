package com.epam.timetracking.dto;

import com.epam.timetracking.entities.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO implements Serializable {
    private static final long serialVersionUID = 4388941982703123467L;

    private Integer id;
    private String category;
    private Integer categoryId;

    private String title;
    private String description;

    private LocalDateTime creationDateTime;
    private LocalDateTime lastUpdateDateTime;
    private Time appendTime;
    private Time totalTime;
    private String status;
    private String comment;
    private String userName;

    private List<ActivityStoryDTO> stories = new ArrayList<>();

}
