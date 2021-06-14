package com.epam.timetracking.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

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
