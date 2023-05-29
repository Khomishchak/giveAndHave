package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Notification;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmitterServiceImpl implements  EmitterService{

    List<SseEmitter> emitters = new ArrayList<>();

    @Override
    public void addEmitter(SseEmitter emitter) {
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitters.add(emitter);
    }

    @Override
    public void pushNotification(String name, String from, String message) {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        Notification payload = Notification
                .builder()
                .from(from)
                .message(message)
                .build();

        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter
                        .event()
                        .name(name)
                        .data(payload));

            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });

        emitters.removeAll(deadEmitters);
    }
}
