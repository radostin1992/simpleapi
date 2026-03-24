package com.myproject.simpleapi.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import com.myproject.simpleapi.entities.JokeFromExtApi;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class JokeServiceTest {

    @MockitoBean
    private RestTemplate restTemplate;

    @Autowired
    private JokeService jokeService;

    private static final String FULL_EXT_URL_FOR_TEST = "https://official-joke-api.appspot.com/jokes/programming/random";

    @Test
    public void shouldReturnRandomJoke() {
        JokeFromExtApi joke = new JokeFromExtApi("programming", "Why did the programmer bring a broom to work?",
                "To clean up all the bugs.", 1L);
        JokeFromExtApi[] jokeArr = { joke };

        when(restTemplate.getForObject(FULL_EXT_URL_FOR_TEST, JokeFromExtApi[].class)).thenReturn(jokeArr);
        // when(restTemplate.getForObject(any(), any())).thenReturn(jokeArr);

        String result = jokeService.getRandomJokeFromExtApi("programming");

        System.out.println("TUKA: " + result);

        assert result != null;
        assertTrue(result.contains(joke.setup()));
    }

    @Test
    public void shouldReturnEmptyStringOnFailure() {
        // when(restTemplate.getForObject(any(), any())).thenThrow(new RuntimeException());
        when(restTemplate.getForObject(FULL_EXT_URL_FOR_TEST,
        JokeFromExtApi[].class))
        .thenThrow(new RuntimeException());

        String result = jokeService.getRandomJokeFromExtApi("programming");

        assertTrue("".equals(result));
    }
}
