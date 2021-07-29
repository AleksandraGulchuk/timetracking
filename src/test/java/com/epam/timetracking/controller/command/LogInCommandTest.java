package com.epam.timetracking.controller.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogInCommandTest {

    private static LogInCommand logInCommand;

    @Test
    void isAccessible() {
        Assertions.assertTrue(logInCommand.isAccessible(null));
    }
}