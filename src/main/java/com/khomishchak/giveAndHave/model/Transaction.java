package com.khomishchak.giveAndHave.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp createdAt;

    private int cost;

    @JsonIgnore
    @ManyToMany(mappedBy = "transactions")
    private Set<User> workingPairOfUsers = new HashSet<>();
}
