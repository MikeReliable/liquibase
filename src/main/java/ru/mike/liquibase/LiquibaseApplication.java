package ru.mike.liquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import ru.mike.liquibase.service.LiquibaseService;

import java.io.IOException;


@SpringBootApplication
@EnableCaching
public class LiquibaseApplication {

    public static void main(String[] args) throws IOException {

        LiquibaseService.liquibaseMigration();

        SpringApplication.run(LiquibaseApplication.class, args);
    }


}
