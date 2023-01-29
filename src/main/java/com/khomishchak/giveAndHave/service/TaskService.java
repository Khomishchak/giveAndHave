package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.User;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {

    public Task createTask(Task task, User user);
}
