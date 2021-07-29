package com.epam.timetracking.controller.command.allroles;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.dto.ActivityDTO;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.database.ActivityService;
import com.epam.timetracking.service.database.CategoryService;
import com.epam.timetracking.service.database.UserService;
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
    private final CategoryService categoryService;
    private final ActivityService activityService;
    private final UserService userService;
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
        req.getSession().setAttribute("categories", categoryService.getCategories());
        req.getSession().setAttribute("activities",
                adapter.adaptList(userService.getActivities(user.getId())));
        req.getSession().setAttribute("status", activityService.getActivityStatuses());
        req.getSession().setAttribute("pagePath", PagePath.CLIENT_START);
    }
}
