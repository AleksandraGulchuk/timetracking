package com.epam.timetracking.pojo.entity;

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
public class Activity implements Serializable {
    private static final long serialVersionUID = 4388941982703123467L;

    private Integer id;
    private String category;
    private Integer categoryId;

    private String title;
    private String description;

    private LocalDateTime creationDateTime;
    private LocalDateTime lastUpdateDateTime;
    private Long appendTime;
    private Long totalTime;
    private String status;
    private String comment;
    private String userName;

    private List<ActivityStory> stories = new ArrayList<>();
}
