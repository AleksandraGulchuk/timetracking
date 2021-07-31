package com.epam.timetracking.service.database;

import com.epam.timetracking.exception.ServiceException;
import com.epam.timetracking.pojo.entity.Category;
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
public class CategoryService {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private static final Logger log = LogManager.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(DBConfig dbConfig, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        dataSource = dbConfig.getDataSource();
    }

    /**
     * Gets a list of all Category objects.
     *
     * @return - a list of all Category objects.
     */
    public List<Category> getCategories() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return jdbcTemplate.query(connection, SQLQueries.SELECT_CATEGORIES,
                    new ResultSetRowMapper<>(Category.class));
        } catch (SQLException exception) {
            log.error("Cannot get categories: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot get categories!", exception);
        }
    }

    /**
     * Adds new Category object.
     *
     * @param category - Category object.
     */
    public void addCategory(Category category) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(connection, SQLQueries.INSERT_CATEGORY, new Object[]{category.getCategory()});
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot add category: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot add category: " + exception.getMessage(), exception);
        }
    }

    /**
     * Deletes Category object by id.
     *
     * @param categoryId - category id.
     */
    public void deleteCategory(int categoryId) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            jdbcTemplate.update(connection, SQLQueries.DELETE_CATEGORY_BY_ID,
                    new Integer[]{categoryId});
            connection.commit();
        } catch (SQLException exception) {
            log.error("Cannot delete category: " + exception.getMessage(), exception);
            throw new ServiceException("Cannot delete category: " + exception.getMessage(), exception);
        }
    }

}
