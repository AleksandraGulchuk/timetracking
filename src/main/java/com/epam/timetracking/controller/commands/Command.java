package com.epam.timetracking.controller.commands;

import com.epam.timetracking.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException;
    boolean isAccessible(String role);

}
