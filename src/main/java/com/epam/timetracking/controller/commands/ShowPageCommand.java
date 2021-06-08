package com.epam.timetracking.controller.commands;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException {

        Object pagePath = req.getSession().getAttribute("pagePath");
        if (pagePath == null) {
            return PagePath.START_PAGE_COMMAND;
        }
        return pagePath.toString();
    }

    @Override
    public boolean isAccessible(String role) {
        return true;
    }
}
