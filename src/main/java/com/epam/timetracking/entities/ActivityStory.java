package com.epam.timetracking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityStory implements Serializable {
    private static final long serialVersionUID = -2265143675115428210L;

    private Integer id;
    private Integer activityId;
    private LocalDateTime updateDateTime;
    private Long timeSpent;
    private String comment;
}
