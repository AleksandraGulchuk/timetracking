package com.epam.timetracking.controller.commands.admin;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class ProcessUserRequest extends AdminCommand {
    private final AdminService adminService;
    private static final Logger log = LogManager.getLogger(ProcessUserRequest.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String requestType = req.getParameter("requestType");
        int requestId = Integer.parseInt(req.getParameter("requestId"));
        String choose = req.getParameter("choose");
        String comment = req.getParameter("comment");
        adminService.processUserRequest(requestType, requestId, choose, comment);
        req.setAttribute("message", "Request processed successfully");
        req.getSession().setAttribute("pagePath", "controller?command=goToUsersRequests");
        return PagePath.MESSAGE;
    }
}
