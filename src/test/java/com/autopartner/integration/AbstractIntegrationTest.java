package com.autopartner.integration;

import com.autopartner.api.dto.response.AuthenticationResponse;
import com.autopartner.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.autopartner.api.dto.request.AuthenticationRequestFixture.createAuthRequest;
import static com.autopartner.api.dto.request.CompanyRegistrationRequestFixture.createCompanyRegistrationRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public abstract class AbstractIntegrationTest {

  public static PostgreSQLContainer<?> DB_CONTAINER = new PostgreSQLContainer<>("postgres:14.2-alpine");

  static {
    DB_CONTAINER.start();
  }

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  CompanyService companyService;

  String token;

  @BeforeEach
  void setUp() throws Exception {
    if (companyService.findAll().isEmpty()) {
      companyService.create(createCompanyRegistrationRequest());
    }
    auth();
  }

  private void auth() throws Exception {
    MvcResult result = this.mockMvc.perform(post("/api/v1/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createAuthRequest())))
        .andExpect(status().is2xxSuccessful()).andReturn();

    String response = result.getResponse().getContentAsString();
    token = "Bearer " + objectMapper.readValue(response, AuthenticationResponse.class).getToken();
  }

  @DynamicPropertySource
  public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", DB_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", DB_CONTAINER::getUsername);
    registry.add("spring.datasource.password", DB_CONTAINER::getPassword);
  }

}
