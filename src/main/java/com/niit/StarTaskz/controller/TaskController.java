package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.task.Task;
import com.niit.StarTaskz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("star-taskz/api/task")
public class TaskController {

    @Autowired
    UserService userService;

    @GetMapping("/all/{userId}")
    protected ResponseEntity<List<Task>> getAllTasks(@PathVariable String userId){
        return new ResponseEntity<>(userService.getAllTasks(userId), HttpStatus.FOUND);
    }

    @PostMapping("/add/{userId}")
    protected ResponseEntity<Task> addTask(@PathVariable String userId,@RequestBody Task task){
        task.setCreatedAt(LocalDateTime.now());
        return new ResponseEntity<>(userService.addTask(userId,task),HttpStatus.CREATED);
    }

}
