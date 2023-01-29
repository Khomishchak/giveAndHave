package com.khomishchak.giveAndHave.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "tasks")
public class Task {

    @Id
    private Long id;

    private String subject;
    private String taskDescription;

    private int price;

    @ManyToOne
    @JsonIgnore
    private User user;
}
