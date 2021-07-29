package com.epam.timetracking.controller.command.allroles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StartPageCommandTest {

    private static StartPageCommand startPageCommand;

    @Test
    void isAccessibleAdmin() {
        Assertions.assertTrue(startPageCommand.isAccessible("admin"));
    }

    @Test
    void isAccessibleClient() {
        Assertions.assertTrue(startPageCommand.isAccessible("client"));
    }

    @Test
    void isAccessibleNull() {
        Assertions.assertFalse(startPageCommand.isAccessible(null));
    }

}