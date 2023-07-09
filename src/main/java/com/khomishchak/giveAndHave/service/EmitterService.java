package com.khomishchak.giveAndHave.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public interface EmitterService {

    void addEmitter(SseEmitter sseEmitter);

    void pushNotification(String name, String from, String message);
}
