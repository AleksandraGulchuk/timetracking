package com.epam.timetracking.controller.command.admin;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
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

class GoToManagementTest {
    private static GoToManagement goToManagement;
    private static AdminService adminService;
    private static ClientService clientService;


    @BeforeAll
    public static void setUp() {
        adminService = Mockito.mock(AdminService.class);
        clientService = Mockito.mock(ClientService.class);
        goToManagement = new GoToManagement(adminService, clientService);
    }

    @Test
    void testExecute() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(clientService.getCategories()).thenReturn(Collections.emptyList());
        Mockito.when(adminService.getUsers()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(PagePath.MANAGEMENT, goToManagement.execute(req, resp));
    }
}