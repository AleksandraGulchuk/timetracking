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
import java.util.Enumeration;

class ProcessUserRequestTest {
    private static AdminService adminService;
    private static ProcessUserRequest processUserRequest;

    @BeforeAll
    public static void setUp() {
        adminService = Mockito.mock(AdminService.class);
        processUserRequest = new ProcessUserRequest(adminService);
    }

    @Test
    void testExecute() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getParameter("choice")).thenReturn("confirm");
        Mockito.when(req.getParameter("requestType")).thenReturn("delete");
        Mockito.when(req.getParameter("id")).thenReturn("1");
        Mockito.when(req.getParameter("comment")).thenReturn("comment");
        Enumeration<String> parameterNames = Mockito.mock(Enumeration.class);
        Mockito.when(parameterNames.hasMoreElements()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(parameterNames.nextElement()).thenReturn("requestType").thenReturn("id").thenReturn("comment");
        Mockito.when(req.getParameterNames()).thenReturn(parameterNames);
        Assertions.assertEquals(PagePath.MESSAGE, processUserRequest.execute(req, resp));
    }
}