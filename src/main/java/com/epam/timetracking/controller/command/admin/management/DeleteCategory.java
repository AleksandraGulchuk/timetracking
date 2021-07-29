package com.epam.timetracking.controller.command.admin.management;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.command.admin.AdminCommand;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.service.database.CategoryService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class DeleteCategory extends AdminCommand {
    private final CategoryService categoryService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        categoryService.deleteCategory(categoryId);
        req.getSession().setAttribute("message", "Category deleted successfully");
        req.getSession().setAttribute("nextPagePath", PagePath.GO_TO_MANAGEMENT_COMMAND);
        return PagePath.MESSAGE;
    }
}
