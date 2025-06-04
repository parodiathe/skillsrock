package com.Makushev.controllers;

import com.Makushev.exception.UserException;
import com.Makushev.models.User;
import com.Makushev.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/createNewUser")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUserById(@RequestParam("userID") UUID UUID) throws UserException {
        User user = userService.getUserById(UUID);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/userDetailsUpdate")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) throws UserException {
        User updatedUser = userService.updateUserById(user.getUUID(), user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUserById(@RequestParam("userID") UUID UUID) throws UserException {
        userService.deleteUser(UUID);
        return new ResponseEntity<>("User deleted", HttpStatus.ACCEPTED);
    }
}
