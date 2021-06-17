package com.epam.timetracking.controller.command.admin;

import com.epam.timetracking.controller.PagePath;
import com.epam.timetracking.controller.util.Sorter;
import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.bean.UserRequestDTO;
import com.epam.timetracking.pojo.entity.UserRequest;
import com.epam.timetracking.service.AdminService;
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
        req.getSession().setAttribute("pagePath", PagePath.REQUESTS);
        return PagePath.REQUESTS;
    }


}
