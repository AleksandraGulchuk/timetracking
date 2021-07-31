package com.epam.timetracking.controller.command.allroles;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("logOut")
public class LogOutCommand extends AllRolesCommand {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        req.getSession().invalidate();
        return PagePath.LOGIN;
    }

}
