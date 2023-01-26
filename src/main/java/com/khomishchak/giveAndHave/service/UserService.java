package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    public User saveUser(User user);

    public boolean deleteUser(Long id);

    public Optional<User> findById(Long id);


    public Transaction assignTransactionToUsers(User sender, User receiver, Transaction transaction);
}
