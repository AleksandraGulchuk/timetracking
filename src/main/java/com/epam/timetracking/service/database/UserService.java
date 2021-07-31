package com.epam.timetracking.service.database;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.Adapter;
import com.epam.timetracking.pojo.dto.ActivityDTO;
import com.epam.timetracking.pojo.entity.Activity;
import com.epam.timetracking.pojo.entity.User;
import com.epam.timetracking.service.database.util.DBConfig;
import com.epam.timetracking.service.database.util.JdbcTemplate;
import com.epam.timetracking.service.database.util.ResultSetRowMapper;
import com.epam.timetracking.service.database.util.SQLQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private static final Logger log = LogManager.getLogger(UserService.class);

    @Autowired
    public UserService(DBConfig dbConfig, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        dataSource = dbConfig.getDataSource();
    }

    /**
     * Gets User object by login and password.
     *
     * @param login    - user login.
     * @param password - user password.
     * @return - User object.
     */
    public User getUser(String login, String password) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            User user = jdbcTemplate.queryOne(connection, SQLQueries.SELECT_A_USER_BY_LOGIN_AND_PASSWORD,
                    new String[]{login, password},
                    new ResultSetRowMapper<>(User.class));
            if (user == null) {
                throw new ServiceException("User not found by login and password!");
            }
            List<Activity> activities = getActivities(user.getId());
            user.setActivities(new Adapter<>(Activity.class, ActivityDTO.class).adaptList(activities));
            return user;
        } catch (SQLException exception) {
            log.error("User not found by login and password: " + exception.getMessage(), exception);
            throw new ServiceException("User not found by login and password!", exception);
        }
    }

    /**
     * Gets a list of all Activity objects by user id.
     *
     * @param userId - user id.
     * @return - a list of all Activity objects assigned to the user with userId.
     */
    public List<Activity> getActivities(int userId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection,
                    SQLQueries.SELECT_ACTIVITIES_BY_USER_ID,
                    new Integer[]{userId},
                    new ResultSetRowMapper<>(Activity.class));
        } catch (SQLException exception) {
            log.error("Cannot get activities: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get activities!", exception);
        }
    }

    /**
     * Gets a list of all User objects.
     *
     * @return - a list of all User objects.
     */
    public List<User> getUsers() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection, SQLQueries.SELECT_USERS,
                    new ResultSetRowMapper<>(User.class));
        } catch (SQLException exception) {
            log.error("Cannot get users: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get users: " + exception.getMessage(), exception);
        }
    }

    /**
     * Adds new User object.
     *
     * @param user - User object.
     */
    public void addUser(User user) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(
                    connection,
                    SQLQueries.INSERT_USER,
                    new Object[]{
                            user.getName(),
                            user.getLogin(),
                            user.getPassword(),
                            user.getRoleId()
                    });
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot add user: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot add user: " + exception.getMessage(), exception);
        }
    }

    /**
     * Deletes User object by id.
     *
     * @param userId - user id.
     */
    public void deleteUser(int userId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(connection, SQLQueries.DELETE_USER_BY_ID,
                    new Integer[]{userId});
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot delete user: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot delete user: " + exception.getMessage(), exception);
        }
    }
}
