package com.epam.timetracking.controller.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Arrays;
import java.util.List;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        String path = ctx.getRealPath("WEB-INF/log/app.log");
        System.setProperty("fileName", path);
        Logger log = LogManager.getLogger(ContextListener.class);
        log.info("Log fileName: > " + path);

        ctx.setAttribute("app", ctx.getContextPath());

        List<String> locales = Arrays.asList(ctx.getInitParameter("locale-list").split(" "));
        ctx.setAttribute("locales", locales);
    }
}
