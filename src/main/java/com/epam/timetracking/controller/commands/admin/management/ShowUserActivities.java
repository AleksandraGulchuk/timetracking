package com.epam.timetracking.controller.commands.admin.management;

import com.epam.timetracking.controller.commands.admin.AdminCommand;
import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.dto.ActivityDTO;
import com.epam.timetracking.dto.Adapter;
import com.epam.timetracking.entities.Activity;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.ClientService;
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
