package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    public Transaction createTransaction(User sender, User receiver, int cost);

    public List<Transaction> getAllTransaction();
}
