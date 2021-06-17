package com.epam.timetracking.controller.command.admin;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

class GoToUsersRequestsTest {

    private static AdminService adminService;
    private static GoToUsersRequests goToUsersRequests;

    @BeforeAll
    public static void setUp() {
        adminService = Mockito.mock(AdminService.class);
        goToUsersRequests = new GoToUsersRequests(adminService);
    }

    @Test
    void testExecute() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(adminService.getUserRequests()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(PagePath.REQUESTS, goToUsersRequests.execute(req, resp));
    }
}