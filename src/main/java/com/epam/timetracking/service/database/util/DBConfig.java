package com.epam.timetracking.service.database.util;

import javax.sql.DataSource;

public interface DBConfig {
    DataSource getDataSource();
}
