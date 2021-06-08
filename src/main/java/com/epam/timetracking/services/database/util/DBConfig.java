package com.epam.timetracking.services.database.util;

import javax.sql.DataSource;

public interface DBConfig {
    DataSource getDataSource();
}
