package com.niit.StarTaskz.service;

import com.niit.StarTaskz.model.User;
import com.niit.StarTaskz.model.task.Task;
import com.niit.StarTaskz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User registerUser(User user){
        return userRepo.save(user);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User getOneUser(String id){
        if(userRepo.findById(id).isPresent())
            return userRepo.findById(id).get();
        return null;
    }
    // update email
    public User updateEmail(String userId, String email){
       User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));;
        user.setEmail(email);
        return userRepo.save(user);
    }
    // update names
    public User updateNames(String userId, String firstName, String lastName){
       User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));;
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userRepo.save(user);
    }

    public User updatePassword(String userId, String password){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found!"));
        user.setPassword(password);
        userRepo.save(user);
        return user;
    }

    public User updateDateOfBirth(String userId, Date dateOfBirth){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found!"));
        user.setDateOfBirth(dateOfBirth);
        return userRepo.save(user);
    }
    //--------------------------- TASKS -----------------------------
    public List<Task> getAllTasks(String userId){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found!"));
        return user.getUserTasks();
    }

    // add a task
    public Task addTask(String userId, Task task){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not found!"));
        List<Task> userTasks = user.getUserTasks();
        userTasks.add(task);
        user.setUserTasks(userTasks);
        User updatedUser = userRepo.save(user);
        for(Task task1 : updatedUser.getUserTasks()){
            if(task1.getTaskName().equals(task.getTaskName())){
                return task;
            }
        }
        return null;
    }

    //update taskName
    public Task updateTaskName(String userId, String taskName){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not found!"));
        List<Task> taskList = user.getUserTasks();
        Task specificTask;
        for (Task task: taskList){
            if(task.getTaskName().equals(taskName)){
                specificTask = task;
                specificTask.setTaskName(taskName);
                user.setUserTasks(taskList);
                userRepo.save(user);
                return specificTask;
            }
        }
        return null;
    }
}
