package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {

        return userRepository.findAll();
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
    public User findById(Long id) throws ChangeSetPersister.NotFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        return user;
    }

    @Override
    public boolean assignTransactionToUsers(User sender, User receiver, Transaction transaction) {

        Set<Transaction> senderTransactions = sender.getTransactions();
        Set<Transaction> receiverTransactions = receiver.getTransactions();

        senderTransactions.add(transaction);
        receiverTransactions.add(transaction);

        sender.setTransactions(senderTransactions);
        receiver.setTransactions(receiverTransactions);

        userRepository.save(sender);
        userRepository.save(receiver);

        return true;
    }
}
