package com.jdbctest;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class H2DB {
    @Bean
    public DataSource getDataSource(){
        JdbcDataSource h2dataSource = new JdbcDataSource();
        h2dataSource.setUser("test");
        h2dataSource.setPassword("еуые");
        h2dataSource.setURL("jdbc:h2:~/test");
        return h2dataSource;
    }
}
