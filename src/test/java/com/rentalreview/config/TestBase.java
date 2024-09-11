package com.rentalreview.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@ExtendWith(TestEnvironmentExtension.class)
@ResourceLock(Environment.ID)
public abstract class TestBase {

    protected static String jdbcUrl;
    protected static String dbUsername;
    protected static String dbPassword;

    @BeforeAll
    static void prepareTestEnvironment(Environment resource) {
        jdbcUrl = resource.getJdbcUrl();
        dbUsername = resource.getDbUsername();
        dbPassword = resource.getDbPassword();
    }

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("DB_JDBC_URL", () -> jdbcUrl);
        registry.add("DB_USERNAME", () -> dbUsername);
        registry.add("DB_PASSWORD", () -> dbPassword);
    }
}
