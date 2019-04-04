package com.mrhmt.server.services;

import com.mrhmt.server.entities.User;
import com.mrhmt.server.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.springframework.data.jpa.domain.Specification.where;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Page<User> findAllUser(String search, int page, int size) {
        Pageable sortedByCreatedDateDesc =PageRequest.of(page, size, Sort.by("createdDate").descending());
        return userRepository.findAll(where(UserRepository.filterByName(search)),sortedByCreatedDateDesc);
    }

    public User createUser(User user) {
        Optional<User> duplicateUser = userRepository.findByName(user.getName());

        if (duplicateUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account exist!");
        } else {
            user.setRole("ROLE_USER");
            user.setActive(true);
            user.setPassword(encoder.encode(user.getPassword()));

            return userRepository.save(user);
        }
    }

    public User getUserByUserName(String name) {
        return userRepository.findByName(name).get();
    }
}
