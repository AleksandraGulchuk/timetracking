package com.epam.timetracking.pojo.bean;

import com.epam.timetracking.pojo.entity.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO implements Serializable {

    private static final long serialVersionUID = 5486992086519500975L;
    private Integer id;
    private LocalDateTime requestDateTime;
    private String userName;
    private Integer activityId;
    private String category;
    private String title;
    private String description;
    private Time totalTime;
    private String status;
    private String comment;
    private String requestType;

}
