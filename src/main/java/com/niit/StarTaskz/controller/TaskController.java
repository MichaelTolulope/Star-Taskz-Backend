package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.task.Task;
import com.niit.StarTaskz.model.task.TaskSteps;
import com.niit.StarTaskz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("star-taskz/api/task")
public class TaskController {

    @Autowired
    UserService userService;

    @GetMapping("/all/{userId}")
    protected ResponseEntity<List<Task>> getAllTasks(@PathVariable String userId){
        return new ResponseEntity<>(userService.getAllTasks(userId), HttpStatus.FOUND);
    }

    @GetMapping("/single-task/{userId}/{taskId}")
    protected ResponseEntity<Task> getSingleTask(@PathVariable String userId,@PathVariable String taskId){
        return new ResponseEntity<>(userService.getSingleTask(userId,taskId), HttpStatus.FOUND);
    }

    @PostMapping("/add/{userId}")
    protected ResponseEntity<Task> addTask(@PathVariable String userId,@RequestBody Task task){
        task.setCreatedAt(LocalDateTime.now());
        task.setId(UUID.randomUUID().toString());
        return new ResponseEntity<>(userService.addTask(userId,task),HttpStatus.CREATED);
    }

    @PutMapping("/updateStatus/{userId}/{taskId}")
    protected ResponseEntity<Task> updateStatus(@PathVariable String userId, @PathVariable String taskId, @RequestParam String status){
        return new ResponseEntity<>(userService.updateStatus(userId,taskId,status),HttpStatus.OK);
    }

    @DeleteMapping("/delete-task/{userId}/{taskId}")
    protected ResponseEntity<List<Task>> deleteTask(@PathVariable String userId, @PathVariable String taskId){
       return new ResponseEntity<>(userService.deleteTask(userId,taskId), HttpStatus.OK);
    }


    //------------------------------ SUB TASKS

    @PostMapping("/add-subTasks/{userId}/{taskId}")
    protected ResponseEntity<Task> addSubTask(@PathVariable String userId, @PathVariable String taskId, @RequestBody TaskSteps subTask){
        return new ResponseEntity<>(userService.addSubTasks(userId,taskId,subTask),HttpStatus.CREATED);
    }

    @PutMapping("/update-subTask/{userId}/{taskId}/{subTaskId}")
    protected ResponseEntity<Task> updateSubTask(@PathVariable String userId, @PathVariable String taskId, @PathVariable String subTaskId, @RequestParam String status){
        return new ResponseEntity<>(userService.updateTaskStepStatus(userId,taskId,subTaskId,status),HttpStatus.OK);
    }

    @PutMapping("/update-taskName/{userId}/{taskId}")
    protected ResponseEntity<Task> updateTaskName(@PathVariable String userId, @PathVariable String taskId, @RequestParam String taskName){
        return new ResponseEntity<>(userService.updateTaskName(userId,taskId,taskName), HttpStatus.OK);
    }

    @PutMapping("/update-taskDateTime/{userId}/{taskId}")
    protected ResponseEntity<Task> updateTaskDateTime(@PathVariable String userId, @PathVariable String taskId,@RequestParam LocalDateTime dateTime){
        return new ResponseEntity<>(userService.updateStartTime(userId, taskId, dateTime),HttpStatus.OK);
    }

    @DeleteMapping("/delete-subTask/{userId}/{taskId}/{subTaskId}")
    protected ResponseEntity<Task> deleteSubTask(@PathVariable String userId, @PathVariable String taskId, @PathVariable String subTaskId){
        return new ResponseEntity<>(
                userService.deleteTaskStep(userId,taskId,subTaskId),HttpStatus.OK
        );
    }

}
