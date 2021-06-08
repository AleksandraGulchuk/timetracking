package com.epam.timetracking.controller.commands.admin.management;

import com.epam.timetracking.controller.commands.admin.AdminCommand;
import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.AdminService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class DeleteUser extends AdminCommand {
    private final AdminService adminService;
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        adminService.deleteUser(userId);
        req.setAttribute("message", "User deleted successfully");
        req.getSession().setAttribute("pagePath", PagePath.GO_TO_MANAGEMENT_COMMAND);
        return PagePath.MESSAGE;
    }
}
