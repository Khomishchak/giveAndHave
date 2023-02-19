package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.dto.UserDto;
import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.model.security.AuthenticationResponse;
import com.khomishchak.giveAndHave.model.security.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public AuthenticationResponse createUser(UserDto userDto);

    public List<User> getAllUsers();

    public AuthenticationResponse authenticate(LoginRequest loginRequest);

    public void deleteUser(Long id);

    public User findById(Long id);

    public User findByName(String name);


    public Transaction assignTransactionToUsers(User sender, User receiver, Transaction transaction);
}
