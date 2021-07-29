package com.epam.timetracking.controller.command.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GoToReportsTest {
    private static GoToReports goToReports;

    @Test
    void isAccessibleAdmin() {
        Assertions.assertTrue(goToReports.isAccessible("admin"));
    }

    @Test
    void isAccessibleClient() {
        Assertions.assertFalse(goToReports.isAccessible("client"));
    }

    @Test
    void isAccessibleNull() {
        Assertions.assertFalse(goToReports.isAccessible(null));
    }
}