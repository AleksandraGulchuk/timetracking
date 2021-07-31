package com.epam.timetracking.controller.command.admin.management;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.command.admin.AdminCommand;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.service.database.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("deleteActivity")
@RequiredArgsConstructor
public class DeleteActivity extends AdminCommand {

    private final ActivityService activityService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int activityId = Integer.parseInt(req.getParameter("activityId"));
        activityService.deleteActivity(activityId);
        int userId = Integer.parseInt(req.getParameter("userId"));
        req.getSession().setAttribute("message", "Activity deleted successfully");
        req.getSession().setAttribute("nextPagePath", PagePath.SHOW_USER_ACTIVITIES_COMMAND + userId);
        return PagePath.MESSAGE;
    }
}
