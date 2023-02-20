package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task, User user) {

        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }
}
