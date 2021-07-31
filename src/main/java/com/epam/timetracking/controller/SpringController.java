package com.epam.timetracking.controller;


import com.epam.timetracking.controller.command.Command;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller.
 */
@Controller
@RequestMapping("/controller")
public class SpringController {

    private static final Logger log = LogManager.getLogger(SpringController.class);

    @GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.trace("doGet");
        try {
            String path = getPathFromCommand(req, resp);
            log.trace("Path: " + path);
            req.getRequestDispatcher(path).forward(req, resp);
        } catch (ServiceException | NullPointerException e) {
            log.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(PagePath.ERROR).forward(req, resp);
        }
    }

    @PostMapping
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.trace("doPost");
        try {
            String path = getPathFromCommand(req, resp);
            log.trace("Path: " + path);
            req.getSession().setAttribute("pagePath", path);
            resp.sendRedirect(PagePath.SHOW_PAGE_COMMAND + path);
        } catch (ServiceException | NullPointerException e) {
            log.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(PagePath.ERROR).forward(req, resp);
        }
    }

    private String getPathFromCommand(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        String commandName = req.getParameter("command");
        log.trace("Request parameter commandName: " + commandName);
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.epam.timetracking");
        Command command = (Command) context.getBean(commandName);
        log.trace("Command: " + command);
        User user = (User) req.getSession().getAttribute("user");
        log.trace("User: " + user);
        if (user == null) {
            user = createVisitorUser(req);
        }
        if (!command.isAccessible(user.getRole())) {
            log.trace("User's role is not accessible or command is null");
            return PagePath.LOGIN;
        } else {
            String path = command.execute(req, resp);
            log.trace("Path: " + path);
            return path;
        }
    }

    private User createVisitorUser(HttpServletRequest req) {
        User user = new User();
        user.setRole("visitor");
        req.getSession().setAttribute("user", user);
        return user;
    }

}
