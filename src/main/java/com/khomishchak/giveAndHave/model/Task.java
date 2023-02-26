package com.khomishchak.giveAndHave.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String description;

    private int price;

    private boolean isTaskActive;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany
    @JsonIgnore
    private List<Application> applications;
}
