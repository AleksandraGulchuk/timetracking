package com.epam.timetracking.controller.commands.admin;

import com.epam.timetracking.controller.util.Sorter;
import com.epam.timetracking.controller.util.PagePath;
import com.epam.timetracking.dto.Adapter;
import com.epam.timetracking.dto.UserRequestDTO;
import com.epam.timetracking.entities.UserRequest;
import com.epam.timetracking.exceptions.ServiceException;
import com.epam.timetracking.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
public class GoToUsersRequests extends AdminCommand {
    private static final Logger log = LogManager.getLogger(GoToUsersRequests.class);

    private final AdminService adminService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        List<UserRequest> userRequests = adminService.getUserRequests();
        Adapter<UserRequest, UserRequestDTO> adapter = new Adapter<>(UserRequest.class, UserRequestDTO.class);
        log.trace("userRequests = " + userRequests);
        req.getSession().setAttribute("userRequests", adapter.adaptList(userRequests));
        req.getSession().setAttribute("conditions", Sorter.getConditions(UserRequest.class));
        req.getSession().setAttribute("pagePath", "controller?command=goToUsersRequests");
        return PagePath.REQUESTS;
    }


}
