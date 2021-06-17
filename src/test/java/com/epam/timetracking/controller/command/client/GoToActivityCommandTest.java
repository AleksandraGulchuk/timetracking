package com.epam.timetracking.controller.command.client;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class GoToActivityCommandTest {

    private static GoToActivityCommand goToActivityCommand;
    private static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        clientService = Mockito.mock(ClientService.class);
        goToActivityCommand = new GoToActivityCommand(clientService);
    }

    @Test
    void testExecuteNull() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("user")).thenReturn(null);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getParameter("activityId")).thenReturn("1");
        Mockito.when(clientService.getActivity(1)).thenReturn(new Activity());
        Assertions.assertEquals(PagePath.ACTIVITY, goToActivityCommand.execute(req, resp));
    }

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