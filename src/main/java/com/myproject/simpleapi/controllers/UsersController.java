package com.myproject.simpleapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.myproject.openapi.api.UserApi;
import com.myproject.openapi.model.UserRequest;
import com.myproject.openapi.model.UserResponse;
import com.myproject.simpleapi.entities.User;
import com.myproject.simpleapi.services.UsersService;

@Controller
public class UsersController implements UserApi {

    @Autowired
    private UsersService usersService;

    Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Override
    public ResponseEntity<UserResponse> getUser(Integer id) {
        logger.info("Received request for user with id {}", id);

        User result = usersService.findUserById(id);

        if (result == null) {
            logger.warn("User with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(convertToUserResponse(result));
    }

    @Override
    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        logger.info("Received request to create user");

        User createdUser = usersService.createUser(userRequest);

        if (createdUser == null) {
            logger.error("Failed to create user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToUserResponse(createdUser));
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Integer id, UserRequest userRequest) {
        logger.info("Received update request for user with id {}", id);

        User updatedUser = usersService.updateUser(id, userRequest);

        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(convertToUserResponse(updatedUser));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Integer id){
        logger.info("Received delete request for user with id {}", id);

        boolean deleted = usersService.deleteUser(id);

        return ResponseEntity.status(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }

    // Utility method to convert User entity to UserResponse DTO
    public UserResponse convertToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}
