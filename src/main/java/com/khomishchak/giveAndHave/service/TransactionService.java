package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    public Transaction createTransaction(User sender, User receiver, int cost);
}
