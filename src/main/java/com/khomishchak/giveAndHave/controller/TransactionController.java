package com.khomishchak.giveAndHave.controller;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.service.TransactionService;
import com.khomishchak.giveAndHave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
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

    @GetMapping("/get/transaction/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getLatestTransactions() {

        List.of(5,6,7,8,9).forEach(System.out::println);


        return transactionService.getAllTransaction();
    }
}
