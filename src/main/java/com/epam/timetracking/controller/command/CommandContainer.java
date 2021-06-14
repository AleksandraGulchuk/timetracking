package com.epam.timetracking.controller.command;

import com.epam.timetracking.controller.command.admin.GoToManagement;
import com.epam.timetracking.controller.command.admin.GoToReports;
import com.epam.timetracking.controller.command.admin.GoToUsersRequests;
import com.epam.timetracking.controller.command.admin.ProcessUserRequest;
import com.epam.timetracking.controller.command.admin.management.*;
import com.epam.timetracking.controller.command.allroles.FilterByCommand;
import com.epam.timetracking.controller.command.allroles.LogOutCommand;
import com.epam.timetracking.controller.command.allroles.SortByCommand;
import com.epam.timetracking.controller.command.allroles.StartPageCommand;
import com.epam.timetracking.controller.command.client.*;
import com.epam.timetracking.service.AdminService;
import com.epam.timetracking.service.ClientService;
import com.epam.timetracking.service.database.DBAdminService;
import com.epam.timetracking.service.database.DBClientService;
import com.epam.timetracking.service.database.TomcatDBConfig;

import java.util.HashMap;

/**
 * Holder for all commands.
 */
public class CommandContainer {

    private static final HashMap<String, Command> commands = new HashMap<>();
    private static final ClientService CLIENT_SERVICE = new DBClientService(TomcatDBConfig.getInstance());
    private static final AdminService ADMIN_SERVICE = new DBAdminService(TomcatDBConfig.getInstance());

    static {
        commands.put("logIn", new LogInCommand(CLIENT_SERVICE));
        commands.put("logOut", new LogOutCommand());
        commands.put("sortBy", new SortByCommand<>());
        commands.put("filterBy", new FilterByCommand<>());
        commands.put("startPageCommand", new StartPageCommand(CLIENT_SERVICE));
        commands.put("showPageCommand", new ShowPageCommand());
        commands.put("setLocale", new SetLocaleCommand());

        commands.put("goToActivity", new GoToActivityCommand(CLIENT_SERVICE));
        commands.put("requestCreateActivity", new RequestCreateActivity(CLIENT_SERVICE));
        commands.put("requestDeleteActivity", new RequestDeleteActivity(CLIENT_SERVICE));
        commands.put("appendTimeActivity", new AppendTimeActivity(CLIENT_SERVICE));
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

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
