package com.epam.timetracking.controller.command.admin.management;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.command.admin.AdminCommand;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.service.database.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("deleteUser")
@RequiredArgsConstructor
public class DeleteUser extends AdminCommand {

    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        userService.deleteUser(userId);
        req.getSession().setAttribute("message", "User deleted successfully");
        req.getSession().setAttribute("nextPagePath", PagePath.GO_TO_MANAGEMENT_COMMAND);
        return PagePath.MESSAGE;
    }
}
