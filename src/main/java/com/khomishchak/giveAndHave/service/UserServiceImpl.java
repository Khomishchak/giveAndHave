package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.dto.UserDto;
import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.model.UserRole;
import com.khomishchak.giveAndHave.model.security.AuthenticationResponse;
import com.khomishchak.giveAndHave.model.security.LoginRequest;
import com.khomishchak.giveAndHave.repository.UserRepository;
import com.khomishchak.giveAndHave.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private static final int INITIAL_BALANCE = 100;

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse createUser(UserDto userDto) {

        User user = User.builder()
                .name(userDto.getName())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .groupName(userDto.getGroupName())
                .age(userDto.getAge())
                .balance(INITIAL_BALANCE)
                .transactions(userDto.getTransactions())
                .tasks(userDto.getTasks())
                .userRole(UserRole.ROLE_USER)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.createToken(new UserDetailsImpl(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getSenderAndReceiver(Long transactionId) {

        return findPairByIdsOrThrowException(transactionId);
    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getName(),
                        loginRequest.getPassword()
                )
        );

        User user = findByName(loginRequest.getName());
        String jwtToken = jwtService.createToken(new UserDetailsImpl(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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
    public User findByName(String name) {

        return findByNameOrThrowException(name);
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

    private User findByNameOrThrowException(String name) {

        return userRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }

    private List<User> findPairByIdsOrThrowException(Long transactionId) {

        List<Long> usersIds = userRepository.findSenderAndReceiver(transactionId);

        List<User> pairInTransaction = new ArrayList<>();

        usersIds.forEach((id) -> pairInTransaction.add(userRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("User Not Found")
                )));

        return pairInTransaction;
    }

}
