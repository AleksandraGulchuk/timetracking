package com.epam.timetracking.entities;

import com.epam.timetracking.dto.ActivityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -185157957207809488L;

    private Integer id;
    private String name;
    private String login;
    private String password;
    private String role;
    private Integer roleId;
    private List<ActivityDTO> activities = new ArrayList<>();
}
