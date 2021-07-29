package com.epam.timetracking.controller.command.admin.management;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.command.admin.AdminCommand;
import com.epam.timetracking.controller.util.RequestMapper;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.Category;
import com.epam.timetracking.service.database.CategoryService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AddCategory extends AdminCommand {
    private final CategoryService categoryService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Category category = new RequestMapper<>(Category.class).map(req);
        categoryService.addCategory(category);
        req.getSession().setAttribute("message", "Category created successfully");
        req.getSession().setAttribute("nextPagePath", PagePath.GO_TO_MANAGEMENT_COMMAND);
        return PagePath.MESSAGE;
    }
}
