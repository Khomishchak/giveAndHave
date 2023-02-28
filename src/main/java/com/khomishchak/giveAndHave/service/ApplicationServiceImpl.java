package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Application;
import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ApplicationServiceImpl implements ApplicationService{

    private ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> findRequestedForUserTasks(Long userId) {
        return applicationRepository.findApplicationsByUserId(userId);
    }

    @Override
    public int findNewMessagesAmount(Long userId) {
        return applicationRepository.findNewMessagesAmount(userId);
    }
}
