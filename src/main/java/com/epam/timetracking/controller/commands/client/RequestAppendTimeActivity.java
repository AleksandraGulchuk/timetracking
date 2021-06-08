package com.epam.timetracking.controller.commands.client;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.controller.util.RequestMapper;
import com.epam.timetracking.dto.ActivityDTO;
import com.epam.timetracking.dto.Adapter;
import com.epam.timetracking.entities.Activity;
import com.epam.timetracking.entities.User;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class RequestAppendTimeActivity extends ClientCommand {

    private final ClientService clientService;
    private static final Logger log = LogManager.getLogger(RequestAppendTimeActivity.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        try {
            ActivityDTO activityDTO = new RequestMapper<>(ActivityDTO.class).map(req);
            log.trace("ActivityDTO: " + activityDTO);
            int activityId = Integer.parseInt(req.getParameter("id"));
            User user = (User) req.getSession().getAttribute("user");
            clientService.sentRequestAppendActivityTime(user,
                    new Adapter<>(ActivityDTO.class, Activity.class).adapt(activityDTO));
            req.setAttribute("message", "Request sent successfully");
            req.getSession().setAttribute("pagePath", PagePath.GO_TO_ACTIVITY_COMMAND + activityId);
            return PagePath.MESSAGE;
        } catch (NumberFormatException e) {
            log.error(e);
            throw new ServiceException("Request must contain activity info!");
        }
    }
}
