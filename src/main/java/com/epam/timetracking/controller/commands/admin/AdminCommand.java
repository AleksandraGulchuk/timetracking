package com.epam.timetracking.controller.commands.admin;

import com.epam.timetracking.controller.commands.Command;

public abstract class AdminCommand implements Command {
    protected static final String accessRole = "admin";

    @Override
    public boolean isAccessible(String role) {
        return accessRole.equals(role);
    }
}
