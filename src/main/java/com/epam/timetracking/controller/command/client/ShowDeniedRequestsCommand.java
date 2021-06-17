package com.epam.timetracking.controller.command.client;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.bean.DeniedRequest;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
public class ShowDeniedRequestsCommand extends ClientCommand {
    private final ClientService clientService;
    private static final Logger log = LogManager.getLogger(ShowDeniedRequestsCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        User user = (User) req.getSession().getAttribute("user");
        int userId = user.getId();
        List<DeniedRequest> requests = clientService.getDeniedRequests(userId);
        req.setAttribute("requests", requests);
        req.getSession().setAttribute("pagePath", PagePath.SHOW_DENIED_REQUESTS_COMMAND);
        return PagePath.DENIED_REQUESTS;
    }
}
