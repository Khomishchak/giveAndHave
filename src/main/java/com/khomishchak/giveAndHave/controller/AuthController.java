package com.khomishchak.giveAndHave.controller;

import com.khomishchak.giveAndHave.model.security.AuthenticationResponse;
import com.khomishchak.giveAndHave.model.security.LoginRequest;
import com.khomishchak.giveAndHave.model.security.RegistrationRequest;
import com.khomishchak.giveAndHave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity create(@RequestBody @Valid RegistrationRequest registrationRequest, BindingResult result) {

        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        AuthenticationResponse authenticationResponse = userService.createUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {

        return userService.authenticate(loginRequest);
    }
}
