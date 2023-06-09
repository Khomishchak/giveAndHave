package com.khomishchak.giveAndHave.model.security;

import com.khomishchak.giveAndHave.annotations.UniqueEmail;
import com.khomishchak.giveAndHave.annotations.UniqueName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    @UniqueName
    private String name;
    private String password;

    @UniqueEmail
    private String email;

    private String groupName;

    private int age;
}
