package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.User;
import com.niit.StarTaskz.model.dto_classes.user.EmailDTO;
import com.niit.StarTaskz.model.dto_classes.user.UserDateOfBirthDTO;
import com.niit.StarTaskz.model.dto_classes.user.UserFullnameDTO;
import com.niit.StarTaskz.model.dto_classes.user.UserPasswordDTO;
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
// Tested & Trusted
    @PostMapping("/register")
    protected ResponseEntity<User> registerUser(@RequestBody User user) {
        user.setUserTasks(new ArrayList<>());
        User userToBeSaved = userService.registerUser(user);
        return new ResponseEntity<>(userToBeSaved, HttpStatus.CREATED);
    }

    // Tested & Trusted
    @GetMapping("/get-all")
    protected ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

// Tested & Trusted
    @GetMapping("/{id}")
    protected ResponseEntity<User> getSingleUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.getOneUser(id), HttpStatus.FOUND);
    }

// update email endpoint -  Tested & Trusted
    @PutMapping("/update-email/{userId}")
    protected ResponseEntity<User> updateEmail(@PathVariable String userId, @RequestBody EmailDTO emailEntity) {
        String email = emailEntity.getEmail();
        return new ResponseEntity<>( userService.updateEmail(userId, email), HttpStatus.OK);
    }

    // Endpoint to update first name - Tested & Trusted
    @PutMapping("/update-names/{userId}")
    protected ResponseEntity<User> updateFirstName(@PathVariable String userId, @RequestBody UserFullnameDTO fullName) {
        String firstName = fullName.getFirstName();
        String lastName = fullName.getLastName();
        return new ResponseEntity<>(userService.updateNames(userId, firstName, lastName),HttpStatus.OK);
    }


    // Endpoint to update password - Tested & Trusted
    @PutMapping("/update-password/{userId}")
    protected ResponseEntity<User> updatePassword(@PathVariable String userId, @RequestBody UserPasswordDTO passwordEntity) {
        String password = passwordEntity.getPassword();
        return new ResponseEntity<>(userService.updatePassword(userId, password),HttpStatus.OK);
    }

    // Endpoint to update date of birth - Tested & Trusted
    @PutMapping("/update-dateOfBirth/{userId}")
    protected ResponseEntity<User> updateDateOfBirth(@PathVariable String userId, @RequestBody UserDateOfBirthDTO dateOfBirthEntity) {
        Date dateOfBirth = dateOfBirthEntity.getDateOfBirth();
        return new ResponseEntity<>(userService.updateDateOfBirth(userId, dateOfBirth),HttpStatus.OK);
    }

}
