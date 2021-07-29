package com.epam.timetracking.controller.command.admin;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.util.RequestMapper;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.dto.UserRequestDTO;
import com.epam.timetracking.pojo.entity.UserRequest;
import com.epam.timetracking.service.database.UserRequestsService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class ProcessUserRequest extends AdminCommand {
    private final UserRequestsService userRequestsService;
    private static final Logger log = LogManager.getLogger(ProcessUserRequest.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String choice = req.getParameter("choice");
        UserRequestDTO userRequestDTO = new RequestMapper<>(UserRequestDTO.class).map(req);
        UserRequest userRequest = new Adapter<>(UserRequestDTO.class, UserRequest.class).adapt(userRequestDTO);
        log.trace("Request: " + userRequest + " choice: " + choice);
        userRequestsService.processUserRequest(userRequest, choice);

        req.getSession().setAttribute("message", "Request processed successfully");
        req.getSession().setAttribute("nextPagePath", PagePath.GO_TO_USERS_REQUESTS_COMMAND);
        return PagePath.MESSAGE;
    }
}
