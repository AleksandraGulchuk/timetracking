package com.epam.timetracking.controller.util;

import com.epam.timetracking.controller.ControllerServlet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RequestMapperTest {
    HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

    @BeforeAll
    public static void initLog4j(){
        String path = "WEB-INF/log/app.log";
        System.setProperty("fileName", path);
    }

    @Test
    public void test() throws IOException {
        when(req.getParameter("fn")).thenReturn("Vinod");
        when(req.getParameter("ln")).thenReturn("Kashyap");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(resp.getWriter()).thenReturn(pw);

        ControllerServlet controllerServlet =new ControllerServlet();


//        controllerServlet.doGet(req, resp);
//        String result = sw.getBuffer().toString().trim();
//        assertEquals(result, new String("Full Name: Vinod Kashyap"));
    }


//    @Test
//    public void testMap(){
//        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
//        req.setAttribute("id", 1);
//        req.setAttribute("name", "Ivanov Ivan");
//        req.setAttribute("login", "ivanov");
//        req.setAttribute("password", 0);
//        req.setAttribute("role", "admin");
//        req.setAttribute("roleId", 1);
//
////        Mockito.when(req.getParameter("id")).thenReturn("1");
////        Mockito.when(req.getParameter("name")).thenReturn("Ivanov Ivan");
////        Mockito.when(req.getParameter("login")).thenReturn("ivanov");
////        Mockito.when(req.getParameter("password")).thenReturn("0");
////        Mockito.when(req.getParameter("role")).thenReturn("admin");
////        Mockito.when(req.getParameter("roleId")).thenReturn("1");
//
////        RequestMapper requestMapper = Mockito.mock(RequestMapper.class);
//
//
//        User userExpected = new User(1, "Ivanov Ivan", "ivanov", "0", "admin", 1, null);
//
//        Enumeration<String> parameterNames = Mockito.mock(Enumeration.class);
//
//        Mockito.doReturn(parameterNames).when(req).getParameterNames();
//
//
//        User userActual = new RequestMapper<>(User.class).map(req);
//
//        Assertions.assertEquals(userExpected, userActual);
//
//    }


    @AfterAll
    public static void clearPropertyLog4j(){
        System.clearProperty("fileName");
    }
}
