package com.khomishchak.giveAndHave.dto;

import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String name;
    private String password;
    private String email;

    private String groupName;

    private int age;

    private int balance = 100;

    private boolean isVerified = false;

    private UserRole userRole;

    private Set<Transaction> transactions = new HashSet<>();

    private Set<Task> tasks = new HashSet<>();
}
