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

class ShowPageCommandTest {

    private static ShowPageCommand showPageCommand;

    @BeforeAll
    public static void setUp() {
        showPageCommand = new ShowPageCommand();
    }

    @Test
    public void testExecute() throws ServiceException, IOException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("pagePath")).thenReturn(PagePath.ACTIVITY);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Assertions.assertEquals(PagePath.ACTIVITY, showPageCommand.execute(req, resp));
    }

    @Test
    public void testExecuteNullPath() throws ServiceException, IOException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("pagePath")).thenReturn(null);
        Mockito.when(session.getAttribute("nextPagePath")).thenReturn(PagePath.ACTIVITY);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Assertions.assertEquals(PagePath.ACTIVITY, showPageCommand.execute(req, resp));
    }

    @Test
    public void testExecuteNullNextPath() throws ServiceException, IOException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("pagePath")).thenReturn(null);
        Mockito.when(session.getAttribute("nextPagePath")).thenReturn(null);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Assertions.assertEquals(PagePath.START_PAGE_COMMAND, showPageCommand.execute(req, resp));
    }

    @Test
    void isAccessible() {
        Assertions.assertTrue(showPageCommand.isAccessible(null));
    }
}