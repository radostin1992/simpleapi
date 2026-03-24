package com.myproject.simpleapi.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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

import com.myproject.simpleapi.security.Authenticator;
import com.myproject.simpleapi.services.JokeService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class JokeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private Authenticator authenticator;

    @MockitoBean
    private JokeService jokeService;

    @Test
    public void getJoke() throws Exception {
        String joketype = "programming";

        when(jokeService.getRandomJokeFromExtApi("programming")).thenReturn("joke");

        mvc.perform(get("/joke").accept(MediaType.TEXT_PLAIN)
                .headers(authenticator.getAuthenticatedHeader()).param("joketype", joketype))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));

        verify(jokeService).getRandomJokeFromExtApi(joketype);
    }

    @Test
    public void tryGettingAJokeWithInvalidJokeType() throws Exception {
        String joketype = "invalid-type";

        mvc.perform(get("/joke").accept(MediaType.TEXT_PLAIN)
                .headers(authenticator.getAuthenticatedHeader()).param("joketype", joketype))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid joketype")));

        verifyNoInteractions(jokeService);
    }

    @Test
    public void tryToGetAJokeWhenThereIsAProblemWithService() throws Exception {
        String joketype = "programming";

        when(jokeService.getRandomJokeFromExtApi("programming")).thenReturn("");

        mvc.perform(get("/joke").accept(MediaType.TEXT_PLAIN)
                .headers(authenticator.getAuthenticatedHeader()).param("joketype", joketype))
                .andExpect(status().isFailedDependency())
                .andExpect(content().string(containsString("Problem getting a joke, sorry :(")));

        verify(jokeService).getRandomJokeFromExtApi(joketype);
    }
}
