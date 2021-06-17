package com.epam.timetracking.controller.command.allroles;

import com.epam.timetracking.controller.util.Sorter;
import com.epam.timetracking.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Sort command.
 * Type parameters:
 * <T> – the type of objects to be sorted
 */
public class SortByCommand<T> extends AllRolesCommand {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String condition = req.getParameter("condition");
        String listName = req.getParameter("listName");
        List<T> list = (List<T>) req.getSession().getAttribute(listName);
        if (!list.isEmpty()) {
            Class<T> clazz = (Class<T>) list.get(0).getClass();
            list = new Sorter<>(clazz).sort(list, condition);
            req.getSession().setAttribute(listName, list);
        }
        return String.valueOf(req.getSession().getAttribute("pagePath"));
    }
}
