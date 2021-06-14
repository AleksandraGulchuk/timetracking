package com.epam.timetracking.controller.command;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Show page command.
 *
 **/
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
