package com.epam.timetracking.controller.command;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

class SetLocaleCommandTest {

    private static SetLocaleCommand setLocaleCommand;

    @BeforeAll
    public static void setUp() {
        setLocaleCommand = new SetLocaleCommand();
    }

    @Test
    public void testExecute() throws ServiceException, IOException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("pagePath")).thenReturn(PagePath.START_PAGE_COMMAND);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getParameter("locale")).thenReturn("en");
        Mockito.when(req.getSession()).thenReturn(session);
        Assertions.assertEquals(PagePath.START_PAGE_COMMAND, setLocaleCommand.execute(req, resp));
    }

    @Test
    public void testExecuteNullPath() throws ServiceException, IOException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("pagePath")).thenReturn(null);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getParameter("locale")).thenReturn("en");
        Mockito.when(req.getSession()).thenReturn(session);
        Assertions.assertEquals(PagePath.LOGIN, setLocaleCommand.execute(req, resp));
    }

    @Test
    void isAccessible() {
        Assertions.assertTrue(setLocaleCommand.isAccessible(null));
    }
}