package com.epam.timetracking.controller.commands;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.entities.User;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.ClientService;
import com.epam.timetracking.services.database.util.Fields;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LogInCommand implements Command{

    private final ClientService clientService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String login = req.getParameter(Fields.LOGIN);
        String password = req.getParameter(Fields.PASSWORD);
        User user = clientService.getUser(login, password);
        req.getSession().setAttribute("user", user);
        req.getSession().setAttribute("pagePath", PagePath.START_PAGE_COMMAND);
        return PagePath.START_PAGE_COMMAND;
    }

    @Override
    public boolean isAccessible(String role) {
        return true;
    }
}
