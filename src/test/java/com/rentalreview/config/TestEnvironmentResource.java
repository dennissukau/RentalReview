package com.rentalreview.config;

import lombok.Getter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

@Getter
public class TestEnvironmentResource extends Environment implements ExtensionContext.Store.CloseableResource, AutoCloseable {

    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("username")
            .withPassword("password");

    public TestEnvironmentResource() {
        database.start();

        this.setJdbcUrl(database.getJdbcUrl());
        this.setDbUsername(database.getUsername());
        this.setDbPassword(database.getPassword());
    }

    @Override
    public void close() {
        database.stop();
    }
}
