package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    public Task createTask(Task task, User user);

    public List<Task> getAllTasks();

    public void postRequestForTask(Long userId, Long taskId);
}
