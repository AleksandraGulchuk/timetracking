package com.epam.timetracking.controller.commands.admin;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.dto.ActivityDTO;
import com.epam.timetracking.dto.ActivityStoryDTO;
import com.epam.timetracking.dto.Adapter;
import com.epam.timetracking.entities.Activity;
import com.epam.timetracking.entities.ActivityStory;
import com.epam.timetracking.entities.User;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.AdminService;
import com.epam.timetracking.services.ClientService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoToReports extends AdminCommand {

    private final AdminService adminService;
    private final ClientService clientService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        List<Activity> activities = adminService.getActivities();
        Adapter<Activity, ActivityDTO> adapter = new Adapter<>(Activity.class, ActivityDTO.class);
        List<ActivityDTO> activityDTOList = adapter.adaptList(activities);
        for (int i = 0; i < activityDTOList.size(); i++) {
            activityDTOList.get(i)
                    .setStories(new Adapter<>(ActivityStory.class, ActivityStoryDTO.class)
                            .adaptList(activities.get(i).getStories()));
        }
        req.getSession().setAttribute("activities", activityDTOList);
        req.getSession().setAttribute("categories", clientService.getCategories());
        req.getSession().setAttribute("activityStatuses", clientService.getActivityStatuses());
        List<User> users = adminService.getUsers()
                .stream()
                .filter(user -> !user.getRole().equals("admin"))
                .collect(Collectors.toList());
        req.getSession().setAttribute("users", users);
        req.getSession().setAttribute("pagePath", PagePath.REPORTS);
        return PagePath.REPORTS;
    }
}