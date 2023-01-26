package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {

        user.setTransactions(new HashSet<>());
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long id) {

        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }

        return true;
    }

    @Override
    public Optional<User> findById(Long id) {

        return userRepository.findById(id);
    }

    @Override
    public Transaction assignTransactionToUsers(User sender, User receiver, Transaction transaction) {

        Set<Transaction> senderTransactions = sender.getTransactions();
        Set<Transaction> receiverTransactions = receiver.getTransactions();

        senderTransactions.add(transaction);
        receiverTransactions.add(transaction);

        sender.setTransactions(senderTransactions);
        receiver.setTransactions(receiverTransactions);

        userRepository.save(sender);
        userRepository.save(receiver);

        return transaction;
    }
}
