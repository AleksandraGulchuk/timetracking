package com.epam.timetracking.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Integer id;
    private LocalDateTime requestDateTime;
    private String userName;
    private Integer activityId;
    private String category;
    private String title;
    private String description;
    private Long totalTime;
    private String status;
    private String comment;
    private String requestType;

}
