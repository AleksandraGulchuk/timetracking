package com.epam.timetracking.controller.command.admin;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.dto.ActivityDTO;
import com.epam.timetracking.pojo.dto.ActivityStoryDTO;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.ActivityStory;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.database.ActivityService;
import com.epam.timetracking.service.database.CategoryService;
import com.epam.timetracking.service.database.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component("goToReports")
@RequiredArgsConstructor
public class GoToReports extends AdminCommand {

    private final ActivityService activityService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        List<Activity> activities = activityService.getActivities();
        Adapter<Activity, ActivityDTO> adapter = new Adapter<>(Activity.class, ActivityDTO.class);
        List<ActivityDTO> activityDTOList = adapter.adaptList(activities);
        for (int i = 0; i < activityDTOList.size(); i++) {
            activityDTOList.get(i)
                    .setStories(new Adapter<>(ActivityStory.class, ActivityStoryDTO.class)
                            .adaptList(activities.get(i).getStories()));
        }
        req.getSession().setAttribute("activities", activityDTOList);
        req.getSession().setAttribute("categories", categoryService.getCategories());
        req.getSession().setAttribute("activityStatuses", activityService.getActivityStatuses());
        List<User> users = userService.getUsers()
                .stream()
                .filter(user -> !user.getRole().equals("admin"))
                .collect(Collectors.toList());
        req.getSession().setAttribute("users", users);
        req.getSession().setAttribute("pagePath", PagePath.REPORTS);
        return PagePath.REPORTS;
    }
}
