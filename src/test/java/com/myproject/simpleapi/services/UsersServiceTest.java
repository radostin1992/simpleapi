package com.myproject.simpleapi.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.myproject.openapi.model.UserRequest;
import com.myproject.simpleapi.entities.User;
import com.myproject.simpleapi.repositories.UsersRepository;

import jakarta.inject.Inject;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class UsersServiceTest {
    @MockitoBean
    private UsersRepository usersRepository;

    @Inject
    private UsersService usersService;

    @Test
    public void getExistingUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@example.com");
        when(usersRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User result = usersService.findUserById(1);

        assert result != null;
        assert result.getId() == user.getId();
        assert result.getName().equals(user.getName());
        assert result.getEmail().equals(user.getEmail());
    }

    @Test
    public void getNonExistingUser() throws Exception {
        when(usersRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        User result = usersService.findUserById(1);

        assertNull(result);
    }

    @Test
    public void createUser() throws Exception {
        when(usersRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User saved = invocation.getArgument(0);
                    saved.setId(1L); // simulate JPA behavior
                    return saved;
                });

        UserRequest userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setEmail("john@example.com");
        User result = usersService.createUser(userRequest);

        assertNotNull(result);
        assertNotNull(result.getId());
        assert result.getName().equals(userRequest.getName());
        assert result.getEmail().equals(userRequest.getEmail());
    }

    @Test
    public void createUser2() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("John");
        user.setEmail("john@example.com");

        when(usersRepository.save(any(User.class)))
                .thenReturn(user); // just tell what to return

        UserRequest userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setEmail("john@example.com");
        User result = usersService.createUser(userRequest);

        assertNotNull(result);
        assertNotNull(result.getId());
        assert result.getName().equals(userRequest.getName());
        assert result.getEmail().equals(userRequest.getEmail());
    }

    @Test
    public void createUserWithExistingEmail() throws Exception {
        when(usersRepository.save(any(User.class)))
                .thenReturn(null);

        UserRequest userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setEmail("john@example.com");
        User result = usersService.createUser(userRequest);

        assertNull(result);
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("John Updated");
        user.setEmail("john@example.com");

        when(usersRepository.save(any(User.class)))
                .thenReturn(user);

        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Updated");
        userRequest.setEmail("john@example.com");
        User result = usersService.createUser(userRequest);

        assertNotNull(result);
        assertNotNull(result.getId());
        assert result.getName().equals(userRequest.getName());
        assert result.getEmail().equals(userRequest.getEmail());
    }

    @Test
    public void updateUserUnsuccessfully() throws Exception {
        when(usersRepository.save(any(User.class)))
                .thenReturn(null);

        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Updated");
        userRequest.setEmail("john@example.com");
        User result = usersService.createUser(userRequest);

        assertNull(result);
    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("John Updated");
        user.setEmail("john@example.com");

        when(usersRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        boolean result = usersService.deleteUser(1);

        assertTrue(result);
    }

    @Test
    public void deleteNonExistingUser() throws Exception {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = usersService.deleteUser(1);

        assertFalse(result);
    }
}
