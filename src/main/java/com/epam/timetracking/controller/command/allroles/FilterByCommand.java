package com.epam.timetracking.controller.command.allroles;

import com.epam.timetracking.controller.util.Filter;
import com.epam.timetracking.exception.ServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component("filterBy")
public class FilterByCommand<T> extends AllRolesCommand {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String[] values = req.getParameterValues("value");
        String condition = req.getParameter("condition");
        String listName = req.getParameter("listName");
        List<T> list = (List<T>) req.getSession().getAttribute(listName);
        if (!list.isEmpty()) {
            Class<T> clazz = (Class<T>) list.get(0).getClass();
            list = new Filter<>(clazz).filter(list, condition, values);
            req.getSession().setAttribute(listName, list);
        }
        return String.valueOf(req.getSession().getAttribute("pagePath"));
    }
}
