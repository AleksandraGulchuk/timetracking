package com.epam.timetracking.controller.command.admin;

import com.epam.timetracking.controller.command.Command;

/**
 * The class provides a command interface implementation
 * for accessing administrator commands.
 */
public abstract class AdminCommand implements Command {
    protected static final String ACCESS_ROLE = "admin";

    @Override
    public boolean isAccessible(String role) {
        return ACCESS_ROLE.equals(role);
    }
}
