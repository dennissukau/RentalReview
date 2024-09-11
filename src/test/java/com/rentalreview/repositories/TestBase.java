package com.rentalreview.repositories;

import org.junit.jupiter.api.AfterAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class TestBase {

    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("username")
            .withPassword("password");

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        database.start();
        registry.add("DB_JDBC_URL", database::getJdbcUrl);
        registry.add("DB_USERNAME", database::getUsername);
        registry.add("DB_PASSWORD", database::getPassword);
    }

    @AfterAll
    static void afterAll() {
        database.stop();
    }
}
