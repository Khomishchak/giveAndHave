package com.khomishchak.giveAndHave.Controller;

import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.service.TaskService;
import com.khomishchak.giveAndHave.service.TransactionService;
import com.khomishchak.giveAndHave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
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

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {

        userService.deleteUser(id);
    }

    @PostMapping("/transaction/{senderId}/{receiverId}/{cost}")
    @ResponseStatus(HttpStatus.OK)
    public Transaction makeTransaction(@PathVariable Long senderId, @PathVariable Long receiverId,
                                             @PathVariable int cost) {

        User sender = userService.findById(senderId);
        User receiver = userService.findById(receiverId);

        Transaction transaction = transactionService.createTransaction(sender, receiver, cost);
        return userService.assignTransactionToUsers(sender, receiver, transaction);
    }

    @PostMapping("/create-task/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task, @PathVariable Long userId) {

        User user = userService.findById(userId);

        return taskService.createTask(task, user);
    }
}
