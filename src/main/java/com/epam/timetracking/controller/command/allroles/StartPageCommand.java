package com.epam.timetracking.controller.command.allroles;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.pojo.bean.ActivityDTO;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Start page command.
 **/
@RequiredArgsConstructor
public class StartPageCommand extends AllRolesCommand {
    private final ClientService clientService;
    private static final Logger log = LogManager.getLogger(StartPageCommand.class);


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String path = PagePath.LOGIN;
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            return path;
        }
        String role = user.getRole();
        log.trace("User role: " + role);
        if ("client".equals(role)) {
            setAttributesForClient(req, user);
            req.getSession().setAttribute("pagePath", PagePath.CLIENT_START);
            return PagePath.CLIENT_START;
        }
        if ("admin".equals(role)) {
            req.getSession().setAttribute("pagePath", PagePath.ADMIN_START);
            return PagePath.ADMIN_START;
        }
        return path;
    }

    private void setAttributesForClient(HttpServletRequest req, User user) throws ServiceException {
        Adapter<Activity, ActivityDTO> adapter = new Adapter<>(Activity.class, ActivityDTO.class);
        req.getSession().setAttribute("categories", clientService.getCategories());
        req.getSession().setAttribute("activities",
                adapter.adaptList(clientService.getActivities(user.getId())));
        req.getSession().setAttribute("status", clientService.getActivityStatuses());
        req.getSession().setAttribute("pagePath", PagePath.CLIENT_START);
    }

}
