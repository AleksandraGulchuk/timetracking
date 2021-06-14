package com.epam.timetracking.controller.command.client;

import com.epam.timetracking.controller.command.Command;

/**
 * The class provides a command interface implementation
 * for accessing client commands.
 */
public abstract class ClientCommand implements Command {
    protected static final String accessRole = "client";

    @Override
    public boolean isAccessible(String role) {
        return accessRole.equals(role);
    }
}
