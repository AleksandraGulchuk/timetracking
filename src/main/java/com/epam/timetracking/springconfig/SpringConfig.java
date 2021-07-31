package com.epam.timetracking.springconfig;

import com.epam.timetracking.service.database.TomcatDBConfig;
import com.epam.timetracking.service.database.util.DBConfig;
import com.epam.timetracking.service.database.util.JdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = {"com.epam.timetracking"})
public class SpringConfig {

    @Bean
    public DBConfig dBConfig() {
        return new TomcatDBConfig();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate();
    }

    @Bean
    public InternalResourceViewResolver resolver() {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setPrefix("/WEB-INF/views/*");
        vr.setSuffix(".jsp");
        return vr;
    }

}
