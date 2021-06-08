package com.epam.timetracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeniedRequest {
    private Integer userId;
    private LocalDateTime requestDateTime;
    private String requestType;
    private String category;
    private String title;
    private String comment;
}
