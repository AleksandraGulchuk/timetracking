package com.epam.timetracking.controller.command.admin;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.Category;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.AdminService;
import com.epam.timetracking.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoToManagement extends AdminCommand {
    private static final Logger log = LogManager.getLogger(GoToManagement.class);
    private final AdminService adminService;
    private final ClientService clientService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        List<Category> categories = clientService.getCategories();
        req.getSession().setAttribute("categories", categories);
        log.trace("categories = " + categories);

        List<User> users = adminService.getUsers()
                .stream()
                .filter(user -> !user.getRole().equals("admin"))
                .collect(Collectors.toList());
        req.getSession().setAttribute("users", users);
        log.trace("users = " + users);

        req.getSession().setAttribute("roles", adminService.getRoles());
        req.getSession().setAttribute("pagePath", PagePath.MANAGEMENT);
        return PagePath.MANAGEMENT;
    }

}
