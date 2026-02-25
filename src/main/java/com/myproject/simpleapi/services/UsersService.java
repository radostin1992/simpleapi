package com.myproject.simpleapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.openapi.model.UserRequest;
import com.myproject.simpleapi.entities.User;
import com.myproject.simpleapi.repositories.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    Logger logger = LoggerFactory.getLogger(UsersService.class);

    public User findUserById(int id) {
        User user = usersRepository.findById((long) id).orElse(null);

        return user;
    }

    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        try {
            User savedUser = usersRepository.save(user);
            logger.info("Created user with id {}", savedUser.getId());
            return savedUser;
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage());
            return null;
        }
    }

    public User updateUser(Integer id, UserRequest userRequest) {
        User existingUser = findUserById(id);
        if (existingUser == null) {
            logger.warn("User with id {} not found", id);
            return null;
        }

        existingUser.setName(userRequest.getName());
        existingUser.setEmail(userRequest.getEmail());

        try {
            User updatedUser = usersRepository.save(existingUser);
            logger.info("Updated user with id {}", updatedUser.getId());
            return updatedUser;
        } catch (Exception e) {
            logger.error("Error updating user with id {}: {}", id, e.getMessage());
            return null;
        }

    }

    public boolean deleteUser(Integer id) {
        User existingUser = findUserById(id);
        if (existingUser == null) {
            logger.warn("User with id {} not found", id);
            return false;
        }

        try {
            usersRepository.delete(existingUser);
            logger.info("Deleted user with id {}", id);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting user with id {}: {}", id, e.getMessage());
            return false;
        }
    }
}
