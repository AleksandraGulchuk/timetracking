package com.epam.timetracking.pojo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeniedRequest implements Serializable {
    private static final long serialVersionUID = -7857460655677017586L;
    private Integer userId;
    private LocalDateTime requestDateTime;
    private String requestType;
    private String category;
    private String title;
    private String comment;
}
