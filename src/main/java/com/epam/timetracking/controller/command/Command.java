package com.epam.timetracking.controller.command;

import com.epam.timetracking.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main interface for the Command pattern implementation.
 */
public interface Command {

    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException;

    /**
     * Accession method for command.
     *
     * @param role the role of the user who is requesting the execution of the command.
     * @return role access check result.
     */
    boolean isAccessible(String role);
}
