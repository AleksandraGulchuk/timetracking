package com.epam.timetracking.controller.command.admin.management;

import com.epam.timetracking.controller.command.admin.AdminCommand;
import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.pojo.bean.ActivityDTO;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.service.ClientService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
public class ShowUserActivities extends AdminCommand {

    private final ClientService clientService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String userIdAsString = req.getParameter("userId");
        int userId = Integer.parseInt(userIdAsString);
        Adapter<Activity, ActivityDTO> adapter = new Adapter<>(Activity.class, ActivityDTO.class);
        List<ActivityDTO> activitiesDTO = adapter.adaptList(clientService.getActivities(userId));
        req.getSession().setAttribute("activities", activitiesDTO);
        req.setAttribute("userId", userId);
        req.getSession().setAttribute("pagePath", PagePath.USER_ACTIVITIES);
        return PagePath.USER_ACTIVITIES;
    }
}
