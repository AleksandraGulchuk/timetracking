package com.epam.timetracking.dto;

import com.epam.timetracking.entities.Time;
import com.epam.timetracking.entities.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private Integer id;
    private LocalDateTime requestDateTime;
//    private Integer userId;
//    private String userLogin;
    private String userName;
    private Integer activityId;
    private String category;
    private String title;
    private String description;
//    private LocalDateTime creationDateTime;
//    private LocalDateTime lastUpdateDateTime;
    private Time totalTime;
    private String status;
    private String comment;
    private String requestType;
    private Time appendTime;

}
