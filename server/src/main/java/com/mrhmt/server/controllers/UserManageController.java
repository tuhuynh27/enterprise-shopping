package com.mrhmt.server.controllers;

import com.mrhmt.server.entities.User;
import com.mrhmt.server.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserManageController {
    private final UserRepository userRepository;

    public UserManageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    Iterable<User> listUser() {
        return this.userRepository.findAll();
    }
}
