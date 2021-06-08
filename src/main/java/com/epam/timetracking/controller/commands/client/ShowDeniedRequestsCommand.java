package com.epam.timetracking.controller.commands.client;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.dto.DeniedRequest;
import com.epam.timetracking.entities.User;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.ClientService;
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
        return PagePath.DENIED_REQUESTS;
    }
}
