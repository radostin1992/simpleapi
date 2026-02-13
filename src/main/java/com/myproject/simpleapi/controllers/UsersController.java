package com.myproject.simpleapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.simpleapi.entities.User;
import com.myproject.simpleapi.repositories.UsersRepository;

@RestController
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    Logger logger = LoggerFactory.getLogger(UsersController.class);

    @GetMapping("/user")
    public String getUser(@RequestParam(required = true) String id) {
        logger.info("Received request for user with id {}", id);

        User user = usersRepository.findById(Long.parseLong(id)).orElse(null);
        if (user == null) {
            logger.warn("User with id {} not found", id);
            return "User not found";
        }

        return "User found: " + user.getFirstName() + " with email " + user.getEmail();
    }
}
