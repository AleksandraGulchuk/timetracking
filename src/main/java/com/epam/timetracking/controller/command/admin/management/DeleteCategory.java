package com.epam.timetracking.controller.command.admin.management;

import com.epam.timetracking.controller.command.admin.AdminCommand;
import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.service.AdminService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class DeleteCategory extends AdminCommand {
    private final AdminService adminService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        adminService.deleteCategory(categoryId);
        req.setAttribute("message", "Category deleted successfully");
        req.getSession().setAttribute("pagePath", PagePath.GO_TO_MANAGEMENT_COMMAND);
        return PagePath.MESSAGE;
    }
}
