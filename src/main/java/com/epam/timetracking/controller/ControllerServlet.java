package com.epam.timetracking.controller;


import com.epam.timetracking.controller.commands.Command;
import com.epam.timetracking.controller.commands.CommandContainer;
import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.entities.User;
import com.epam.timetracking.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = -773040610129972128L;
    private static final Logger log = LogManager.getLogger(ControllerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        runProcessing(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        runProcessing(req, resp);
    }


    private void runProcessing(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String commandName = req.getParameter("command");
            log.trace("Request parameter: command --> " + commandName);

            Command command = CommandContainer.getCommand(commandName);
            log.trace("Command: " + command);

            User user = (User) req.getSession().getAttribute("user");
            log.trace("User: " + user);

            if (user == null ) {
                 user = new User();
                 user.setRole("visitor");
                 req.getSession().setAttribute("user", user);
            }

            if (command == null || !command.isAccessible(user.getRole())) {
                log.trace("User's role is not accessible");
                req.getRequestDispatcher(PagePath.LOGIN).forward(req, resp);
                return;
            }
            try {
                String path = command.execute(req, resp);
                log.trace("Path: " + path);
                req.getRequestDispatcher(path).forward(req, resp);

            } catch (ServiceException e) {
                log.error(e.getMessage(), e);
                req.setAttribute("message", e.getMessage());
                req.getRequestDispatcher(PagePath.ERROR).forward(req, resp);
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(PagePath.ERROR).forward(req, resp);
        }
    }

}
