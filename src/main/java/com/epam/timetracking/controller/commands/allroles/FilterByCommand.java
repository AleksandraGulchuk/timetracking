package com.epam.timetracking.controller.commands.allroles;

import com.epam.timetracking.controller.util.Filter;
import com.epam.timetracking.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FilterByCommand<T> extends AllRolesCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String[] values = req.getParameterValues("value");
        String condition = req.getParameter("condition");
        String listName = req.getParameter("listName");
        List<T> list = (List<T>) req.getSession().getAttribute(listName);
        if (!list.isEmpty()) {
            Class<T> clazz = (Class<T>) list.get(0).getClass();
            list = new Filter<T>(clazz).filter(list, condition, values);
            req.setAttribute(listName, list);
            req.getSession().setAttribute(listName, list);
        }
        return String.valueOf(req.getSession().getAttribute("pagePath"));
    }
}
