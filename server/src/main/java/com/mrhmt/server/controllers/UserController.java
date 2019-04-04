package com.mrhmt.server.controllers;

import com.mrhmt.server.entities.User;
import com.mrhmt.server.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get current user
    @GetMapping("/me")
    public ResponseEntity<User> getMe(Principal principal) {
        if (principal != null){
            String name = principal.getName();
            User user =  userService.getUserByUserName(name);

            if (user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/new")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User result = userService.createUser(user);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/admin/user/getAll")
    public ResponseEntity<Page<User>> findAllCategoryAdmin(@RequestParam(value = "search", required = false) String search, @RequestParam(value = "page", required = false) int page, @RequestParam(value = "size", required = false) int size) {
        Page<User> result = userService.findAllUser(search, page, size);
        return ResponseEntity.ok(result);
    }
}
