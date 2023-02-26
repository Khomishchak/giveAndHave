package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Application;
import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.ApplicationRepository;
import com.khomishchak.giveAndHave.repository.TaskRepository;
import com.khomishchak.giveAndHave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private TaskRepository taskRepository;
    private ApplicationRepository applicationRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ApplicationRepository applicationRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
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

    @Override
    public void postRequestForTask(Long userId, Long taskId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException()
        );

        Task task = findTaskOrThrow(taskId);

        Application application = Application.builder()
                .user(user)
                .task(task)
                .build();

        applicationRepository.save(application);
    }

    private Task findTaskOrThrow(Long id) {

        return taskRepository.findById(id).
            orElseThrow(() -> new IllegalArgumentException());
    }
}
