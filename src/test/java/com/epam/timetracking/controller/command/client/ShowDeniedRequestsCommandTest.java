package com.epam.timetracking.controller.command.client;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.bean.DeniedRequest;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

class ShowDeniedRequestsCommandTest {

    private static ShowDeniedRequestsCommand showDeniedRequestsCommand;
    private static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        clientService = Mockito.mock(ClientService.class);
        showDeniedRequestsCommand = new ShowDeniedRequestsCommand(clientService);
    }

    @Test
    public void testExecute() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        User user = new User(1, "name", "admin", null, "admin", 1, null);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(clientService.getDeniedRequests(user.getId())).thenReturn(List.of(new DeniedRequest()));
        Assertions.assertEquals(PagePath.DENIED_REQUESTS, showDeniedRequestsCommand.execute(req, resp));
    }

}