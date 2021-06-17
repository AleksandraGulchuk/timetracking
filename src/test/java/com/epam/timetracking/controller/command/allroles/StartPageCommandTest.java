package com.epam.timetracking.controller.command.allroles;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

class StartPageCommandTest {

    private static StartPageCommand startPageCommand;
    private static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        clientService = Mockito.mock(ClientService.class);
        startPageCommand = new StartPageCommand(clientService);
    }

    @Test
    void testExecuteNull() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("user")).thenReturn(null);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Assertions.assertEquals(PagePath.LOGIN, startPageCommand.execute(req, resp));
    }

    @Test
    void testExecuteAdmin() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(1, "name", "admin", null, "admin", 1, null);
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Assertions.assertEquals(PagePath.ADMIN_START, startPageCommand.execute(req, resp));
    }

    @Test
    void testExecuteClient() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(2, "name", "client", null, "client", 2, null);
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(clientService.getActivities(2)).thenReturn(Collections.emptyList());
        Assertions.assertEquals(PagePath.CLIENT_START, startPageCommand.execute(req, resp));
    }

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