package com.khomishchak.giveAndHave.Controller;

import com.khomishchak.giveAndHave.dto.UserDto;
import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.service.TaskService;
import com.khomishchak.giveAndHave.service.TransactionService;
import com.khomishchak.giveAndHave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ApiController {

    private UserService userService;
    private TransactionService transactionService;
    private TaskService taskService;

    @Autowired
    public ApiController(UserService userService, TransactionService transactionService, TaskService taskService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {

        userService.createUser(userDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        boolean isRemoved = userService.deleteUser(id);

        if(!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transaction/{senderId}/{receiverId}/{cost}")
    public ResponseEntity<?> makeTransaction(@PathVariable Long senderId, @PathVariable Long receiverId,
                                             @PathVariable int cost) {
        User sender;
        User receiver;

        try{
            sender = userService.findById(senderId);
            receiver = userService.findById(receiverId);
        }catch (ChangeSetPersister.NotFoundException exception) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Transaction transaction = transactionService.createTransaction(sender, receiver, cost);
        userService.assignTransactionToUsers(sender, receiver, transaction);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create-task/{userId}")
    public ResponseEntity<?> createTask(@RequestBody Task task, @PathVariable Long userId) {

        User user;

        try{
            user = userService.findById(userId);
        }catch (ChangeSetPersister.NotFoundException exception) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task createdTask = taskService.createTask(task, user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
