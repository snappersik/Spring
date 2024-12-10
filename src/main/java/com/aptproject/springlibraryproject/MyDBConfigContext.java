package com.aptproject.springlibraryproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.aptproject.springlibraryproject.dbexamples.constants.DBConstants.*;

@Configuration
public class MyDBConfigContext {

    @Bean
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://" + DB_HOST + ":" + PORT + "/" +
                DB, USER, PASSWORD);
    }

}
