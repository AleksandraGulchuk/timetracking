package com.epam.timetracking.controller.commands.client;

import com.epam.timetracking.controller.commands.Command;

public abstract class ClientCommand implements Command {
    protected static final String accessRole = "client";

    @Override
    public boolean isAccessible(String role) {
        return accessRole.equals(role);
    }
}
