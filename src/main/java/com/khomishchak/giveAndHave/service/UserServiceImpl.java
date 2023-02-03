package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.dto.UserDto;
import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = User.builder()
                .name(userDto.getName())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .groupName(userDto.getGroupName())
                .age(userDto.getAge())
                .balance(userDto.getBalance())
                .isVerified(userDto.isVerified())
                .transactions(userDto.getTransactions())
                .tasks(userDto.getTasks())
                .build();

        return userRepository.save(user).toDto();
    }

    @Override
    public void deleteUser(Long id) {

        findByIdOrThrowException(id);
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id){

        return findByIdOrThrowException(id);
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

    private User findByIdOrThrowException(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }
}
