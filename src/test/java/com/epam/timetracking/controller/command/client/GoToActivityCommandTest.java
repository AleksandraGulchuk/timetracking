package com.epam.timetracking.controller.command.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GoToActivityCommandTest {

    private static GoToActivityCommand goToActivityCommand;

    @Test
    void isAccessibleAdmin() {
        Assertions.assertFalse(goToActivityCommand.isAccessible("admin"));
    }

    @Test
    void isAccessibleClient() {
        Assertions.assertTrue(goToActivityCommand.isAccessible("client"));
    }

    @Test
    void isAccessibleNull() {
        Assertions.assertFalse(goToActivityCommand.isAccessible(null));
    }
}