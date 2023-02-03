package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.dto.UserDto;
import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    public UserDto createUser(UserDto userDto);

    public void deleteUser(Long id);

    public User findById(Long id);


    public Transaction assignTransactionToUsers(User sender, User receiver, Transaction transaction);
}
