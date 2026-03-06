package com.myproject.simpleapi.controllers;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.myproject.simpleapi.entities.User;
import com.myproject.simpleapi.repositories.UsersRepository;
import com.myproject.simpleapi.security.Authenticator;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class UsersControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UsersRepository usersRepository;

    @Autowired
    private Authenticator authenticator;

    @Test
    public void getExistingUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@example.com");

        when(usersRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        mvc.perform(get("/user").param("id", "1").accept(MediaType.APPLICATION_JSON).headers(authenticator.getAuthenticatedHeader()))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"email\":\"john@example.com\",\"id\":1,\"name\":\"John\"}")));
    }

    @Test
    public void getNonExistingUser() throws Exception {
        when(usersRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        mvc.perform(get("/user").param("id", "1").accept(MediaType.APPLICATION_JSON).headers(authenticator.getAuthenticatedHeader()))
                .andExpect(status().isNotFound());
    }
}
