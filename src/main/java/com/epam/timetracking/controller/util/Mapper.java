package com.epam.timetracking.controller.util;

import javax.servlet.http.HttpServletRequest;

public interface Mapper<T> {

    T map(HttpServletRequest req);

}
