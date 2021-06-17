package com.epam.timetracking.controller.command.allroles;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.util.Filter;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.RequestType;
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
import java.util.List;

class FilterByCommandTest {

    @Test
    void testExecuteEmptyList() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("types")).thenReturn(Collections.emptyList());
        Mockito.when(session.getAttribute("pagePath")).thenReturn(PagePath.LOGIN);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getParameterValues("value")).thenReturn(new String[]{"delete", "creat NEW"});
        Mockito.when(req.getParameter("condition")).thenReturn("type");
        Mockito.when(req.getParameter("listName")).thenReturn("types");
        Mockito.when(req.getSession()).thenReturn(session);
        Assertions.assertEquals(PagePath.LOGIN, new FilterByCommand<>().execute(req, resp));
    }

    @Test
    void testExecute() throws ServiceException {
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("types"))
                .thenReturn(List.of(new RequestType(1,"delete"), new RequestType(2,"creat NEW")));
        Mockito.when(session.getAttribute("pagePath")).thenReturn(PagePath.LOGIN);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getParameterValues("value")).thenReturn(new String[]{"delete", "creat NEW"});
        Mockito.when(req.getParameter("condition")).thenReturn("type");
        Mockito.when(req.getParameter("listName")).thenReturn("types");
        Mockito.when(req.getSession()).thenReturn(session);
        Assertions.assertEquals(PagePath.LOGIN, new FilterByCommand<>().execute(req, resp));
    }

}