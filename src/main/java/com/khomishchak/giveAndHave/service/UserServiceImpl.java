package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.model.UserRole;
import com.khomishchak.giveAndHave.model.security.AuthenticationResponse;
import com.khomishchak.giveAndHave.model.security.LoginRequest;
import com.khomishchak.giveAndHave.model.security.RegistrationRequest;
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

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse createUser(RegistrationRequest registrationRequest) {

        User user = User.builder()
                .name(registrationRequest.getName())
                .password(bCryptPasswordEncoder.encode(registrationRequest.getPassword()))
                .email(registrationRequest.getEmail())
                .groupName(registrationRequest.getGroupName())
                .age(registrationRequest.getAge())
                .balance(INITIAL_BALANCE)
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

        sender.getTransactions().add(transaction);
        receiver.getTransactions().add(transaction);

        userRepository.save(sender);
        userRepository.save(receiver);

        return transaction;
    }

    @Override
    public User updateUser(User user) {

        User updateUser = userRepository.findById(user.getId())
                .map(user1 -> {
                    user1.setEmail(user.getEmail());
                    user1.setGroupName(user.getGroupName());
                    user1.setName(user.getName());
                    return userRepository.save(user1);
                })
                .orElseThrow(
                        IllegalArgumentException::new
                );

        return user;
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
