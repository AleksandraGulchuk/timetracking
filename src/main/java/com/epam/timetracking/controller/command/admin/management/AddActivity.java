package com.epam.timetracking.controller.command.admin.management;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.command.admin.AdminCommand;
import com.epam.timetracking.controller.util.RequestMapper;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.bean.ActivityDTO;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.service.AdminService;
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
        req.getSession().setAttribute("message", "Activity created successfully");
        req.getSession().setAttribute("nextPagePath", PagePath.SHOW_USER_ACTIVITIES_COMMAND + userId);
        return PagePath.MESSAGE;
    }
}
