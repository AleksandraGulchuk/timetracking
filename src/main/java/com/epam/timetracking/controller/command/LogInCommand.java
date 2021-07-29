package com.epam.timetracking.controller.command;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.database.UserService;
import com.epam.timetracking.service.database.util.Fields;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Login command.
 */
@RequiredArgsConstructor
public class LogInCommand implements Command {

    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String login = req.getParameter(Fields.LOGIN);
        String password = req.getParameter(Fields.PASSWORD);
        User user = userService.getUser(login, password);
        req.getSession().setAttribute("user", user);
        req.getSession().setAttribute("pagePath", PagePath.START_PAGE_COMMAND);
        return PagePath.START_PAGE_COMMAND;
    }

    @Override
    public boolean isAccessible(String role) {
        return true;
    }
}
