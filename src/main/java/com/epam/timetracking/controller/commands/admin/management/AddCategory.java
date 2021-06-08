package com.epam.timetracking.controller.commands.admin.management;

import com.epam.timetracking.controller.commands.admin.AdminCommand;
import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.controller.util.RequestMapper;
import com.epam.timetracking.entities.Category;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.AdminService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AddCategory extends AdminCommand {
    private final AdminService adminService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Category category = new RequestMapper<>(Category.class).map(req);
        adminService.addCategory(category);
        req.setAttribute("message", "Category created successfully");
        req.getSession().setAttribute("pagePath", PagePath.GO_TO_MANAGEMENT_COMMAND);
        return PagePath.MESSAGE;
    }
}
