package com.epam.timetracking.controller.commands;

import com.epam.timetracking.controller.commands.admin.*;
import com.epam.timetracking.controller.commands.admin.management.*;
import com.epam.timetracking.controller.commands.allroles.FilterByCommand;
import com.epam.timetracking.controller.commands.allroles.LogOutCommand;
import com.epam.timetracking.controller.commands.allroles.SortByCommand;
import com.epam.timetracking.controller.commands.allroles.StartPageCommand;
import com.epam.timetracking.controller.commands.client.*;
import com.epam.timetracking.services.AdminService;
import com.epam.timetracking.services.database.DBAdminService;
import com.epam.timetracking.services.database.DBClientService;
import com.epam.timetracking.services.ClientService;
import com.epam.timetracking.services.database.TomcatDBConfig;

import java.util.HashMap;

public class CommandContainer {

    private static final HashMap<String, Command> commands = new HashMap<>();
    private static final ClientService CLIENT_SERVICE = new DBClientService(TomcatDBConfig.getInstance());
    private static final AdminService ADMIN_SERVICE = new DBAdminService(TomcatDBConfig.getInstance());

    static {
        commands.put("logIn", new LogInCommand(CLIENT_SERVICE));
        commands.put("logOut", new LogOutCommand());
        commands.put("sortBy", new SortByCommand<>());
        commands.put("filterBy", new FilterByCommand<>());
        commands.put("startPageCommand", new StartPageCommand(CLIENT_SERVICE, ADMIN_SERVICE));
        commands.put("showPageCommand", new ShowPageCommand());
        commands.put("setLocale", new SetLocaleCommand());

        commands.put("goToActivity", new GoToActivityCommand(CLIENT_SERVICE));
        commands.put("requestCreateActivity", new RequestCreateActivity(CLIENT_SERVICE));
        commands.put("requestDeleteActivity", new RequestDeleteActivity(CLIENT_SERVICE));
        commands.put("requestAppendTimeActivity", new RequestAppendTimeActivity(CLIENT_SERVICE));
        commands.put("showDeniedRequests", new ShowDeniedRequestsCommand(CLIENT_SERVICE));

        commands.put("goToUsersRequests", new GoToUsersRequests(ADMIN_SERVICE));
        commands.put("processUserRequest", new ProcessUserRequest(ADMIN_SERVICE));
        commands.put("goToManagement", new GoToManagement(ADMIN_SERVICE, CLIENT_SERVICE));
        commands.put("showUserActivities", new ShowUserActivities(CLIENT_SERVICE));

        commands.put("addUser", new AddUser(ADMIN_SERVICE));
        commands.put("deleteUser", new DeleteUser(ADMIN_SERVICE));
        commands.put("addCategory", new AddCategory(ADMIN_SERVICE));
        commands.put("deleteCategory", new DeleteCategory(ADMIN_SERVICE));
        commands.put("addActivity", new AddActivity(ADMIN_SERVICE));
        commands.put("deleteActivity", new DeleteActivity(ADMIN_SERVICE));

        commands.put("goToReports", new GoToReports(ADMIN_SERVICE, CLIENT_SERVICE));
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
