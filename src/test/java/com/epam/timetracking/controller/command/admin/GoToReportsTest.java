package com.epam.timetracking.controller.command.admin;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.service.AdminService;
import com.epam.timetracking.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

class GoToReportsTest {
    private static GoToReports goToReports;
    private static AdminService adminService;

    @BeforeAll
    public static void setUp() {
        adminService = Mockito.mock(AdminService.class);
        ClientService clientService = Mockito.mock(ClientService.class);
        goToReports = new GoToReports(adminService, clientService);
    }

    @Test
    void testExecute() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(adminService.getActivities()).thenReturn(
                List.of(new Activity(), new Activity())
        );
        Mockito.when(adminService.getUsers()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(PagePath.REPORTS, goToReports.execute(req, resp));
    }

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