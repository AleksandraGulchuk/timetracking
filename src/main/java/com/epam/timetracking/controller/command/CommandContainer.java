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
import com.epam.timetracking.service.database.*;

import java.util.HashMap;

/**
 * Holder for all commands.
 */
public class CommandContainer {

    private static final HashMap<String, Command> commands = new HashMap<>();
    private static final ActivityService ACTIVITY_SERVICE = new ActivityService(TomcatDBConfig.getInstance());
    private static final CategoryService CATEGORY_SERVICE = new CategoryService(TomcatDBConfig.getInstance());
    private static final RoleService ROLE_SERVICE = new RoleService(TomcatDBConfig.getInstance());
    private static final UserRequestsService USER_REQUESTS_SERVICE = new UserRequestsService(TomcatDBConfig.getInstance());
    private static final UserService USER_SERVICE = new UserService(TomcatDBConfig.getInstance());

    private CommandContainer() {
    }

    static {
        commands.put("logIn", new LogInCommand(USER_SERVICE));
        commands.put("logOut", new LogOutCommand());
        commands.put("sortBy", new SortByCommand<>());
        commands.put("filterBy", new FilterByCommand<>());
        commands.put("startPageCommand", new StartPageCommand(CATEGORY_SERVICE, ACTIVITY_SERVICE, USER_SERVICE));
        commands.put("showPageCommand", new ShowPageCommand());
        commands.put("setLocale", new SetLocaleCommand());

        commands.put("goToActivity", new GoToActivityCommand(ACTIVITY_SERVICE));
        commands.put("requestCreateActivity", new RequestCreateActivity(USER_REQUESTS_SERVICE));
        commands.put("requestDeleteActivity", new RequestDeleteActivity(USER_REQUESTS_SERVICE));
        commands.put("appendTimeActivity", new AppendTimeActivity(ACTIVITY_SERVICE));
        commands.put("showDeniedRequests", new ShowDeniedRequestsCommand(USER_REQUESTS_SERVICE));

        commands.put("goToUsersRequests", new GoToUsersRequests(USER_REQUESTS_SERVICE));
        commands.put("processUserRequest", new ProcessUserRequest(USER_REQUESTS_SERVICE));
        commands.put("goToManagement", new GoToManagement(USER_SERVICE, CATEGORY_SERVICE, ROLE_SERVICE));
        commands.put("showUserActivities", new ShowUserActivities(USER_SERVICE));

        commands.put("addUser", new AddUser(USER_SERVICE));
        commands.put("deleteUser", new DeleteUser(USER_SERVICE));
        commands.put("addCategory", new AddCategory(CATEGORY_SERVICE));
        commands.put("deleteCategory", new DeleteCategory(CATEGORY_SERVICE));
        commands.put("addActivity", new AddActivity(ACTIVITY_SERVICE));
        commands.put("deleteActivity", new DeleteActivity(ACTIVITY_SERVICE));

        commands.put("goToReports", new GoToReports(ACTIVITY_SERVICE, CATEGORY_SERVICE, USER_SERVICE));
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
