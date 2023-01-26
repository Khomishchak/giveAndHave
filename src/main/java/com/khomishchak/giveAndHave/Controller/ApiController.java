package com.khomishchak.giveAndHave.Controller;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.service.TransactionService;
import com.khomishchak.giveAndHave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
public class ApiController {

    private UserService userService;
    private TransactionService transactionService;

    @Autowired
    public ApiController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody User user) {

        userService.saveUser(user);

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

        if(userService.findById(senderId).isPresent() && userService.findById(receiverId).isPresent()) {

            sender = userService.findById(senderId).get();
            receiver = userService.findById(receiverId).get();
        }else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Transaction transaction = transactionService.createTransaction(sender, receiver, cost);
        userService.assignTransactionToUsers(sender, receiver, transaction);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    
}
