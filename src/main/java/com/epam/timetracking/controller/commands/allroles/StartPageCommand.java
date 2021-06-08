package com.epam.timetracking.controller.commands.allroles;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.dto.ActivityDTO;
import com.epam.timetracking.dto.Adapter;
import com.epam.timetracking.entities.Activity;
import com.epam.timetracking.entities.User;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.AdminService;
import com.epam.timetracking.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class StartPageCommand extends AllRolesCommand {
    private final ClientService clientService;
    private final AdminService adminService;
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
//            setAttributesForAdmin(req);
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

//    private void setAttributesForAdmin(HttpServletRequest req) throws ServiceException {
////        List<UserNewActivity> userNewActivities = adminService.getUserNewActivities();
////        req.setAttribute("userNewActivities", userNewActivities);
//        //list of change requests
//
//    }

}
