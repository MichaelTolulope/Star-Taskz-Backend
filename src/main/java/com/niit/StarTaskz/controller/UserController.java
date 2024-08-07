package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.dto_classes.collaboration_workspace.JobTitleDTO;
import com.niit.StarTaskz.model.user.User;
import com.niit.StarTaskz.model.dto_classes.user.*;
import com.niit.StarTaskz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("star-taskz/api/user")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;
// Tested & Trusted
    @PostMapping("/register")
    protected ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    // login endpoint
    @PostMapping("/login")
    protected ResponseEntity<User> userLogin(@RequestBody LoginDTO loginDetail){
        String email = loginDetail.getEmail();;
        String password = loginDetail.getPassword();
        return new ResponseEntity<>(userService.loginUser(email,password),HttpStatus.OK);
    }

    // Tested & Trusted
    @GetMapping("/get-all")
    protected ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("Number of users returned: " + users.size());  // Add logging here
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

// Tested & Trusted
    @GetMapping("/{id}")
    protected ResponseEntity<User> getSingleUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.getOneUser(id), HttpStatus.FOUND);
    }
// get user by email
    @GetMapping("/get-by-mail/{email}")
    protected ResponseEntity<User> getSingleUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getOneUserByEmail(email), HttpStatus.FOUND);
    }

    @PostMapping("/upload-profileImage/{userId}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @PathVariable String userId) {
        try {
            String imageUrl = userService.uploadProfilePic(userId,file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Image upload failed");
        }
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

    // Endpoint to update jobTitle - Tested & Trusted
    @PutMapping("/update-jobTitle/{userId}")
    protected ResponseEntity<User> updateJobTitle(@PathVariable String userId, @RequestBody JobTitleDTO jobTitleDTO) {
        String jobTitle = jobTitleDTO.getJobTitle();
        return new ResponseEntity<>(userService.updateJobTitle(userId, jobTitle),HttpStatus.OK);
    }

    // Endpoint to update date of birth - Tested & Trusted
    @PutMapping("/update-dateOfBirth/{userId}")
    protected ResponseEntity<User> updateDateOfBirth(@PathVariable String userId, @RequestBody UserDateOfBirthDTO dateOfBirthEntity) {
        Date dateOfBirth = dateOfBirthEntity.getDateOfBirth();
        return new ResponseEntity<>(userService.updateDateOfBirth(userId, dateOfBirth),HttpStatus.OK);
    }


    // get user by email


}
