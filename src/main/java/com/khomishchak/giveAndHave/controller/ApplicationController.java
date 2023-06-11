package com.khomishchak.giveAndHave.controller;

import com.khomishchak.giveAndHave.model.Application;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/get/tasks-requests/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Application> getTasksRequests(@PathVariable Long id) {
        return applicationService.findRequestedForUserTasks(id);
    }

    @GetMapping("/get/new-messages-amount/{id}")
    @ResponseStatus(HttpStatus.OK)
    public int getNewMessagesAmount(@PathVariable Long id) {
        return applicationService.findNewMessagesAmount(id);
    }

    @PostMapping("/accept/user-for-task/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptUser(@PathVariable Long taskId ,@RequestBody User user) {
        applicationService.acceptUser(taskId, user.getId());
    }
}
