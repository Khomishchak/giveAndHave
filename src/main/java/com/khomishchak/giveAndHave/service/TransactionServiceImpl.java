package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.TransactionRepository;
import com.khomishchak.giveAndHave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {

        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Transaction createTransaction(User sender, User receiver, int cost) {

        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedTimestamp = timestamp.format(myFormatObj);

        Transaction transaction = Transaction.builder()
                .createdAt(formattedTimestamp)
                .cost(cost)
                .build();

        return submitTransaction(sender, receiver, transaction);
    }

    @Override
    public List<Transaction> getAllTransaction() {

        return transactionRepository.findAll();
    }

    private Transaction submitTransaction(User sender, User receiver, Transaction transaction)  {

        changeBalance(sender, receiver, transaction.getCost());

        userRepository.save(sender);
        userRepository.save(receiver);

        return transactionRepository.save(transaction);
    }

    private boolean changeBalance(User sender, User receiver, int amount) {
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        return true;
    }
}
