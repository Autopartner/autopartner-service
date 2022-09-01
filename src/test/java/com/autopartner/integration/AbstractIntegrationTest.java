package com.autopartner.integration;

import com.autopartner.api.auth.JwtVerifier;
import com.autopartner.domain.UserFixture;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = AbstractIntegrationTest.DataSourceInitializer.class)
@Testcontainers
public abstract class AbstractIntegrationTest {

  public static PostgreSQLContainer<?> DB_CONTAINER = new PostgreSQLContainer<>("postgres:14.2-alpine");

  static {
    DB_CONTAINER.start();
  }

  @Autowired
  protected TestRestTemplate testRestTemplate;

  @MockBean
  JwtVerifier tokenService;

  @MockBean
  UserDetailsService userDetailsService;

  @BeforeEach
  void setUp() {
    testRestTemplate.getRestTemplate().setInterceptors(
        Collections.singletonList((request, body, execution) -> {
          request.getHeaders()
              .add(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHJha3V0ZW4uY29tIiwiaXNzIjoidGVzdEByYWt1dGVuLmNvbSIsImV4cCI6NzIwMTY0ODA1MzMxMX0.pI4PBB_FvxO2YWw_QOruFYFH_TJ7s6tyMSiYDFHWsZk");
          return execution.execute(request, body);
        }));

    String username = "test@username.com";
    when(tokenService.verify(any())).thenReturn(username);
    when(userDetailsService.loadUserByUsername(username)).thenReturn(UserFixture.createUser());
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
