package com.epam.timetracking.controller.command;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.ClientService;
import com.epam.timetracking.service.database.util.Fields;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class LogInCommandTest {

    private static LogInCommand logInCommand;
    private static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        clientService = Mockito.mock(ClientService.class);
        logInCommand = new LogInCommand(clientService);
    }

    @Test
    public void testExecute() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getParameter(Fields.LOGIN)).thenReturn("admin");
        Mockito.when(req.getParameter(Fields.PASSWORD)).thenReturn("1");
        Mockito.when(req.getSession()).thenReturn(session);
        User user = new User(1, "name", "admin", null, "admin", 1, null);
        Mockito.when(clientService.getUser("admin", "1")).thenReturn(user);
        Assertions.assertEquals(PagePath.START_PAGE_COMMAND, logInCommand.execute(req, resp));
    }

    @Test
    void isAccessible() {
        Assertions.assertTrue(logInCommand.isAccessible(null));
    }
}