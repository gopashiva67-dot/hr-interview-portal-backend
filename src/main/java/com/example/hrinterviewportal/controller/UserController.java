package com.example.hrinterviewportal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrinterviewportal.entity.User;
import com.example.hrinterviewportal.repository.UserRepository;
import com.example.hrinterviewportal.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(
            UserService userService,
            UserRepository userRepository) {

        this.userService = userService;
        this.userRepository = userRepository;
    }


    // ==========================================
    // REGISTER USER
    // ==========================================

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestBody User user) {

        User savedUser =
                userService.registerUser(user);

        return ResponseEntity.ok(savedUser);
    }


    // ==========================================
    // LOGIN USER
    // ==========================================

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody User loginUser) {

        Optional<User> userOptional =
                userRepository.findByEmail(
                        loginUser.getEmail()
                );

        if (userOptional.isEmpty()) {

            return ResponseEntity
                    .status(401)
                    .body("Invalid email or password.");
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(
                loginUser.getPassword())) {

            return ResponseEntity
                    .status(401)
                    .body("Invalid email or password.");
        }

        return ResponseEntity.ok(user);
    }


    // ==========================================
    // GET ALL USERS
    // ==========================================

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }


    // ==========================================
    // GET USER BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id) {

        return userService
                .getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }
}