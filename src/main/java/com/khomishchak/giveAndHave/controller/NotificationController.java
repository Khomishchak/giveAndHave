package com.khomishchak.giveAndHave.controller;

import com.khomishchak.giveAndHave.dto.NotificationRequest;
import com.khomishchak.giveAndHave.service.EmitterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final static long TIME_OUT_TIME_FOR_EMITTER = 24 * 60 * 60 * 1000l;

    private EmitterService emitterService;

    @Autowired
    public NotificationController(EmitterService emitterService) {
        this.emitterService = emitterService;
    }

    @GetMapping("/subscription")
    public SseEmitter subscribeForNotifications() {

        SseEmitter sseEmitter = new SseEmitter(TIME_OUT_TIME_FOR_EMITTER);
        emitterService.addEmitter(sseEmitter);

        return sseEmitter;
    }

    @PostMapping("/notification/{name}")
    public ResponseEntity<?> sendNotification(@PathVariable String name, @RequestBody NotificationRequest request) {
        emitterService.pushNotification(name, request.getFrom(), request.getMessage());
        return ResponseEntity.ok().body("message pushed to user " + name);
    }
}
