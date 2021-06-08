package com.epam.timetracking.controller.commands.client;

import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.dto.ActivityDTO;
import com.epam.timetracking.dto.ActivityStoryDTO;
import com.epam.timetracking.dto.Adapter;
import com.epam.timetracking.entities.Activity;
import com.epam.timetracking.entities.ActivityStory;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class GoToActivityCommand extends ClientCommand {
    private final ClientService clientService;
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
            if(activity == null){
                throw new ServiceException("Activity not found!");
            }
            ActivityDTO activityDTO = new Adapter<>(Activity.class, ActivityDTO.class).adapt(activity);
            activityDTO.setStories(new Adapter<>(ActivityStory.class, ActivityStoryDTO.class).adaptList(activity.getStories()));

            req.getSession().setAttribute("activity", activityDTO);
            req.getSession().setAttribute("pagePath", PagePath.ACTIVITY);
            return PagePath.ACTIVITY;
        }
        catch (NumberFormatException e){
            log.error(e);
            throw new ServiceException("Request must contain activity id!");
        }
    }
}
