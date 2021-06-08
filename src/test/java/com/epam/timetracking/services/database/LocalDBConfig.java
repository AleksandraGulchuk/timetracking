package com.epam.timetracking.services.database;

import com.epam.timetracking.services.database.util.DBConfig;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class LocalDBConfig implements DBConfig {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/timetracking_test?user=postgres&password=0000";

    private static LocalDBConfig localDBConfig;
    private final DataSource dataSource;

    private LocalDBConfig() {
        try {
            Class.forName(JDBC_DRIVER);
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setUrl(URL);

            dataSource = ds;
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("Cannot init DBManager", exception);
        }
    }

    public static synchronized LocalDBConfig getInstance() {
        if (localDBConfig == null) {
            localDBConfig = new LocalDBConfig();
        }
        return localDBConfig;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
