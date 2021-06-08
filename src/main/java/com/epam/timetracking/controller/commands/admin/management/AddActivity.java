package com.epam.timetracking.controller.commands.admin.management;

import com.epam.timetracking.controller.commands.admin.AdminCommand;
import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.controller.util.RequestMapper;
import com.epam.timetracking.dto.ActivityDTO;
import com.epam.timetracking.dto.Adapter;
import com.epam.timetracking.entities.Activity;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.AdminService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AddActivity extends AdminCommand {
    private final AdminService adminService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ActivityDTO activityDTO = new RequestMapper<>(ActivityDTO.class).map(req);
        Adapter<ActivityDTO, Activity> adapter = new Adapter<>(ActivityDTO.class, Activity.class);
        int userId = Integer.parseInt(req.getParameter("userId"));
        adminService.addActivity(adapter.adapt(activityDTO), userId);
        req.setAttribute("message", "Activity created successfully");
        req.getSession().setAttribute("pagePath", PagePath.SHOW_USER_ACTIVITIES_COMMAND + userId);
        return PagePath.MESSAGE;
    }
}
