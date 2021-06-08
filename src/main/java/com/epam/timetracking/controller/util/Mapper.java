package com.epam.timetracking.controller.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Mapper<T>{
    T map(HttpServletRequest req);
}
