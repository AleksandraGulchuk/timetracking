package com.epam.timetracking.controller.command.client;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.util.RequestMapper;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.dto.ActivityDTO;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.database.UserRequestsService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class RequestDeleteActivity extends ClientCommand {

    private final UserRequestsService userRequestsService;
    private static final Logger log = LogManager.getLogger(RequestCreateActivity.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        try {
            ActivityDTO activityDTO = new RequestMapper<>(ActivityDTO.class).map(req);
            User user = (User) req.getSession().getAttribute("user");
            userRequestsService.sendRequestDeleteActivity(user,
                    new Adapter<>(ActivityDTO.class, Activity.class).adapt(activityDTO));
            int activityId = Integer.parseInt(req.getParameter("id"));
            req.getSession().setAttribute("message", "Request sent successfully");
            req.getSession().setAttribute("nextPagePath", PagePath.GO_TO_ACTIVITY_COMMAND + activityId);
            return PagePath.MESSAGE;
        } catch (NumberFormatException e) {
            log.error(e);
            throw new ServiceException("Request must contain activity info!");
        }
    }
}
