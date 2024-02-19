package com.rewards.app;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;


public class BaseIT {

    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:12").withReuse(true);

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }
}
