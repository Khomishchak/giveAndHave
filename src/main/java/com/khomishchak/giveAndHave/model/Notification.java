package com.khomishchak.giveAndHave.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
    private String from;
    private String message;
}
