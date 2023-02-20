package com.khomishchak.giveAndHave.controller;

import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.service.TaskService;
import com.khomishchak.giveAndHave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {


    private UserService userService;
    private TaskService taskService;

    @Autowired
    public TaskController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @PostMapping("/post/task/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Task postTask(@RequestBody Task task, @PathVariable Long userId) {
        User user = userService.findById(userId);

        return taskService.createTask(task, user);
    }

    @GetMapping("/get/task/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasks() {

        return taskService.getAllTasks();
    }
}
