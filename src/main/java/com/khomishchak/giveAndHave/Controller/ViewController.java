package com.khomishchak.giveAndHave.Controller;

import com.khomishchak.giveAndHave.model.security.AuthenticationResponse;
import com.khomishchak.giveAndHave.model.security.LoginRequest;
import com.khomishchak.giveAndHave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/view")
public class ViewController {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public ViewController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(userService.authenticate(loginRequest)) ;

//        return new ResponseEntity<AuthenticationResponse>(HttpStatus.OK);
    }
}
