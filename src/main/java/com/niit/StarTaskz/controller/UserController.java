package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.User;
import com.niit.StarTaskz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("star-taskz/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    protected ResponseEntity<User> registerUser(@RequestBody User user) {
        user.setUserTasks(new ArrayList<>());
        User userToBeSaved = userService.registerUser(user);
        return new ResponseEntity<>(userToBeSaved, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    protected ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    protected ResponseEntity<User> getSingleUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.getOneUser(id), HttpStatus.FOUND);
    }


    @PutMapping("/{userId}/email")
    protected ResponseEntity<User> updateEmail(@PathVariable String userId, @RequestBody String email) {
        return new ResponseEntity<>( userService.updateEmail(userId, email), HttpStatus.OK);
    }

    // Endpoint to update first name
    @PutMapping("/{userId}/names")
    protected ResponseEntity<User> updateFirstName(@PathVariable String userId, @RequestBody String firstName, @RequestParam String lastName) {
        return new ResponseEntity<>(userService.updateNames(userId, firstName, lastName),HttpStatus.OK);
    }


    // Endpoint to update password
    @PutMapping("/{userId}/password")
    protected ResponseEntity<User> updatePassword(@PathVariable String userId, @RequestBody String password) {
        return new ResponseEntity<>(userService.updatePassword(userId, password),HttpStatus.OK);
    }

    // Endpoint to update date of birth
    @PutMapping("/{userId}/dateOfBirth")
    protected ResponseEntity<User> updateDateOfBirth(@PathVariable String userId, @RequestBody Date dateOfBirth) {
        return new ResponseEntity<>(userService.updateDateOfBirth(userId, dateOfBirth),HttpStatus.OK);
    }

}
