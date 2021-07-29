package com.epam.timetracking.service.database;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.Role;
import com.epam.timetracking.service.database.util.DBConfig;
import com.epam.timetracking.service.database.util.JdbcTemplate;
import com.epam.timetracking.service.database.util.ResultSetRowMapper;
import com.epam.timetracking.service.database.util.SQLQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleService {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private static final Logger log = LogManager.getLogger(RoleService.class);

    public RoleService(DBConfig dbConfig) {
        jdbcTemplate = new JdbcTemplate();
        dataSource = dbConfig.getDataSource();
    }

    /**
     * Gets a list of all Role objects.
     *
     * @return - a list of all Role objects.
     */
    public List<Role> getRoles() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection, SQLQueries.SELECT_ROLES,
                    new ResultSetRowMapper<>(Role.class));
        } catch (SQLException exception) {
            log.error("Cannot get roles: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get roles: " + exception.getMessage(), exception);
        }
    }
}
