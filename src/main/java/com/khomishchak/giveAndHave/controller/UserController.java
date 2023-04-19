package com.khomishchak.giveAndHave.controller;

import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.service.TaskService;
import com.khomishchak.giveAndHave.service.TransactionService;
import com.khomishchak.giveAndHave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {

    private UserService userService;
    private TransactionService transactionService;
    private TaskService taskService;

    @Autowired
    public UserController(UserService userService, TransactionService transactionService, TaskService taskService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.taskService = taskService;
    }

    @GetMapping("/get/user/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/get/user/current")
    @ResponseStatus(HttpStatus.OK)
    public User getCurrentUser(Authentication authentication ) {
        return userService.findByName(authentication.getName());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {

        userService.deleteUser(id);
    }

    @GetMapping("/get/pairInTransaction/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getSenderAndReceiver(@PathVariable Long id) {
        return userService.getSenderAndReceiver(id);
    }

    @PutMapping("/update/user")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user) {
        return this.userService.updateUser(user);
    }
}
