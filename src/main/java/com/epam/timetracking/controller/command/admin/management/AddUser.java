package com.epam.timetracking.controller.command.admin.management;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.command.admin.AdminCommand;
import com.epam.timetracking.controller.util.RequestMapper;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.database.UserService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AddUser extends AdminCommand {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        User user = new RequestMapper<>(User.class).map(req);
        userService.addUser(user);
        req.getSession().setAttribute("message", "User created successfully");
        req.getSession().setAttribute("nextPagePath", PagePath.GO_TO_MANAGEMENT_COMMAND);
        return PagePath.MESSAGE;
    }
}
