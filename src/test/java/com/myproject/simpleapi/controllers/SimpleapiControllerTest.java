package com.myproject.simpleapi.controllers;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.myproject.simpleapi.security.Authenticator;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class SimpleapiControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private Authenticator authenticator;

  @Test
  public void getHello() throws Exception {
    mvc.perform(get("/").accept(MediaType.TEXT_PLAIN).headers(authenticator.getAuthenticatedHeader()))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("Greetings from simple api Spring Boot application!")));
  }
}