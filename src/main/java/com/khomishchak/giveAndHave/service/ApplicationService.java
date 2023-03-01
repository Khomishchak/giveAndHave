package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Application;
import com.khomishchak.giveAndHave.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationService {

    public List<Application> findRequestedForUserTasks(Long userId);

    public int findNewMessagesAmount(Long userId);

    public void acceptUser(Long taskId, Long userId);
}
