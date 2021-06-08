package com.epam.timetracking.controller.commands;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SetLocaleCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException {
        String locale = req.getParameter("locale");
        System.out.println("locale : " + locale);
        HttpSession session = req.getSession();
        session.setAttribute("currentLocale", locale);
        session.setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", locale);
        Object pagePath = req.getSession().getAttribute("pagePath");
        if (pagePath == null) {
            return PagePath.LOGIN;
        }
        return pagePath.toString();
    }

    @Override
    public boolean isAccessible(String role) {
        return true;
    }
}
