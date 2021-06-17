package com.epam.timetracking.controller.command.client;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.util.Pagination;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.bean.ActivityDTO;
import com.epam.timetracking.pojo.bean.ActivityStoryDTO;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.ActivityStory;
import com.epam.timetracking.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class GoToActivityCommand extends ClientCommand {
    private final ClientService clientService;
    private final int RESULTS_PER_PAGE = 10;
    private static final Logger log = LogManager.getLogger(GoToActivityCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String activityId = req.getParameter("activityId");
        if (activityId == null) {
            throw new ServiceException("Request must contain activity id!");
        }
        try {
            int id = Integer.parseInt(activityId);
            Activity activity = clientService.getActivity(id);
            if (activity == null) {
                throw new ServiceException("Activity not found!");
            }
            ActivityDTO activityDTO = new Adapter<>(Activity.class, ActivityDTO.class).adapt(activity);
            activityDTO.setStories(new Adapter<>(ActivityStory.class, ActivityStoryDTO.class).adaptList(activity.getStories()));
            req.getSession().setAttribute("activity", activityDTO);
            Pagination pagination = new Pagination(
                    activityDTO.getStories().size(), RESULTS_PER_PAGE, getPage(req));
            req.getSession().setAttribute("pagination", pagination);
            req.getSession().setAttribute("pagePath", PagePath.ACTIVITY);
            return PagePath.ACTIVITY;
        } catch (NumberFormatException e) {
            log.error(e);
            throw new ServiceException("Request must contain activity id!");
        }
    }

    private int getPage(HttpServletRequest req) {
        String pageAsString = req.getParameter("page");
        int page;
        if (pageAsString == null) {
            page = 1;
        } else {
            page = Integer.parseInt(req.getParameter("page"));
        }
        return page;
    }
}
