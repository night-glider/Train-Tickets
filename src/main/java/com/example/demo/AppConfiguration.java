package com.example.demo;

import com.example.demo.tables.Train;
import com.example.demo.tables.records.TrainRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class AppConfiguration {
    @Bean
    public Connection dbConnection() throws SQLException {
        String userName = "postgres";
        String password = "postgres";
        String url = "jdbc:postgresql://postgres:5432/postgres";

        return DriverManager.getConnection(url, userName, password);
    }

    @Bean
    public DSLContext dbContext() throws SQLException {
        return DSL.using(dbConnection(), SQLDialect.POSTGRES);
    }

}