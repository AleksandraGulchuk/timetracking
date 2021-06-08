package com.epam.timetracking.controller.commands.allroles;

import com.epam.timetracking.controller.commands.Command;

public abstract class AllRolesCommand implements Command {
    private static final String[] accessRoles = new String[]{"admin", "client"};
    @Override
    public boolean isAccessible(String role) {
        for (String accessRole : accessRoles) {
            if(accessRole.equals(role)) return true;
        }
        return false;
    }
}
