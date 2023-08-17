package com.example.demo;

import com.example.demo.tables.Train;
import com.example.demo.tables.records.TrainRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(DemoApplication.class, args);
        /*
        GenerationTool.generate(
                Files.readString(
                        Path.of("jooq-config.xml")
                )
        );
        */
    }

}
