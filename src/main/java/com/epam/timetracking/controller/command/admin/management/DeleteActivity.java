package com.epam.timetracking.controller.command.admin.management;

import com.epam.timetracking.controller.command.admin.AdminCommand;
import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.service.AdminService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class DeleteActivity extends AdminCommand {
    private final AdminService adminService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int activityId = Integer.parseInt(req.getParameter("activityId"));
        adminService.deleteActivity(activityId);
        req.setAttribute("message", "Activity deleted successfully");
        int userId = Integer.parseInt(req.getParameter("userId"));
        req.getSession().setAttribute("pagePath", PagePath.SHOW_USER_ACTIVITIES_COMMAND + userId);
        return PagePath.MESSAGE;
    }
}