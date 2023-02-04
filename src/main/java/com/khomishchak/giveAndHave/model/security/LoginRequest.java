package com.khomishchak.giveAndHave.model.security;

import lombok.Data;

@Data
public class LoginRequest {

    private String name;
    private String password;
}
