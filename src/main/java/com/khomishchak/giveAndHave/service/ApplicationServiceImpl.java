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
public class ApplicationServiceImpl implements ApplicationService{

    private ApplicationRepository applicationRepository;
    private TaskRepository taskRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, TaskRepository taskRepository) {
        this.applicationRepository = applicationRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Application> findRequestedForUserTasks(Long userId) {
        return applicationRepository.findApplicationsByUserId(userId);
    }

    @Override
    public int findNewMessagesAmount(Long userId) {
        return applicationRepository.findNewMessagesAmount(userId);
    }

    @Override
    public void acceptUser(Long taskId, Long userId) {

        Application application = applicationRepository.findByUserIdAndTaskId(userId, taskId).get();
        application.setStatus(true);
        deactivateTask(taskId);
        denyAllExceptAccepted(application);
        applicationRepository.save(application);
    }

    private void denyAllExceptAccepted(Application application) {

        List<Application> applications = applicationRepository.findAllByTaskId(application.getTask().getId());
        applications.remove(application);

        applications.forEach(
                application1 -> {
                    application1.setStatus(false);
                    applicationRepository.save(application1);
                }
        );
    }

    private void deactivateTask(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        task.setTaskActive(false);
        taskRepository.save(task);
    }
}
