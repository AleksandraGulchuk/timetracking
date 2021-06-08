package com.epam.timetracking.services.database.util;

import com.epam.timetracking.exceptions.ServiceException;

import java.sql.ResultSet;

public interface RowMapper<T> {

    T map(ResultSet resultSet);

}
