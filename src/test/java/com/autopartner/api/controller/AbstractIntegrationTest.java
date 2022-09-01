package com.autopartner.api.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = AbstractIntegrationTest.DataSourceInitializer.class)
@Testcontainers
public abstract class AbstractIntegrationTest {

  public static PostgreSQLContainer<?> DB_CONTAINER = new PostgreSQLContainer<>("postgres:14.2-alpine");

  static {
    DB_CONTAINER.start();
  }

  public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.datasource.url=" + DB_CONTAINER.getJdbcUrl(),
          "spring.datasource.username=" + DB_CONTAINER.getUsername(),
          "spring.datasource.password=" + DB_CONTAINER.getPassword()
      );
    }
  }
}
