package com.myproject.simpleapi;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.myproject.simpleapi.entities.User;
import com.myproject.simpleapi.repositories.UsersRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class UsersControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UsersRepository usersRepository;

    @Test
    public void getUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setEmail("john@example.com");

        when(usersRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        mvc.perform(get("/user").param("id", "1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(equalTo("User found: " + user.getFirstName() + " with email " + user.getEmail())));
    }
}
