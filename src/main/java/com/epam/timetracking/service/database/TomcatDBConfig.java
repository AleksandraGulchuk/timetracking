package com.epam.timetracking.service.database;

import com.epam.timetracking.service.database.util.DBConfig;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TomcatDBConfig implements DBConfig {

    @Override
    public DataSource getDataSource() {
        try {
            Context initContext = new InitialContext();
            return (DataSource) initContext.lookup("java:/comp/env/jdbc/timetracking");
        } catch (NamingException exception) {
            throw new IllegalStateException("Cannot init DBManager", exception);
        }
    }
}
