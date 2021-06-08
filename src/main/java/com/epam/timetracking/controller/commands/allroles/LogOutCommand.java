package com.epam.timetracking.controller.commands.allroles;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand extends AllRolesCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        req.getSession().invalidate();
        return PagePath.LOGIN;
    }

}
