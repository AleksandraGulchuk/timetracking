package com.epam.timetracking.controller.comands;

import com.epam.timetracking.controller.commands.LogInCommand;
import com.epam.timetracking.services.database.DBClientService;
import com.epam.timetracking.services.database.LocalDBConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class LoginCommandTest {

    private static LogInCommand logInCommand;

    @BeforeAll
    public static void beforeTest() throws SQLException {
        logInCommand = new LogInCommand(new DBClientService(LocalDBConfig.getInstance()));
    }

//    @Test
//    public void testIsAccessible(){
//        Assertions.assertTrue(logInCommand.isAccessible(null));
//    }
}
