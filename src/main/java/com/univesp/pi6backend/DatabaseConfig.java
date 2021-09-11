//package com.univesp.pi6backend;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DatabaseConfig {
//
//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//
//    @Value("${spring.datasource.driverClassName}")
//    private String dbDriver;
//
//    @Value("${spring.datasource.username}")
//    private String dbUsername;
//
//    @Value("${spring.datasource.password}")
//    private String dbPassword;
//
//
//
//    @Bean
//    public DataSource dataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(dbUrl);
//        config.setDriverClassName(dbDriver);
//        config.setUsername(dbUsername);
//        config.setPassword(dbPassword);
//        return new HikariDataSource(config);
//    }
//
//}
