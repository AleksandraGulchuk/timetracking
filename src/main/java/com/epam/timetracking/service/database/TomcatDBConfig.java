package com.epam.timetracking.service.database;

import com.epam.timetracking.service.database.util.DBConfig;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TomcatDBConfig implements DBConfig {

    private static TomcatDBConfig dBConfigTomcat;
    private final DataSource dataSource;

    private TomcatDBConfig() {
        try {
            Context initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:/comp/env/jdbc/timetracking");
        } catch (NamingException exception) {
            throw new IllegalStateException("Cannot init DBManager", exception);
        }
    }

    public static synchronized TomcatDBConfig getInstance() {
        if (dBConfigTomcat == null) {
            dBConfigTomcat = new TomcatDBConfig();
        }
        return dBConfigTomcat;
    }

    @Override
    public DataSource getDataSource(){
        return dataSource;
    }
}
