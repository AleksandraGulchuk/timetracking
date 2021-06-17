package com.epam.timetracking.controller.command.allroles;

import com.epam.timetracking.controller.command.Command;

/**
 * The class provides a command interface implementation
 * for accessing administrator and client commands.
 */
public abstract class AllRolesCommand implements Command {
    private static final String[] accessRoles = new String[]{"admin", "client"};

    public boolean isAccessible(String role) {
        for (String accessRole : accessRoles) {
            if (accessRole.equals(role)) return true;
        }
        return false;
    }
}
