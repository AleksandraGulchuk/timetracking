package com.epam.timetracking.service.database.util;

import java.sql.ResultSet;

public interface RowMapper<T> {

    T map(ResultSet resultSet);

}
