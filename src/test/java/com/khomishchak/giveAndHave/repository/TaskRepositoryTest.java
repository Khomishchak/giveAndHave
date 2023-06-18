package com.khomishchak.giveAndHave.repository;

import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager.clear();
    }

    @Test
    void shouldReturnListOfTwoUserTasks() {
        //given
        User user = persistUser();
        persistTask(user);
        persistTask(user);

        //when
        List<Long> tasks = taskRepository.findTaskIdByUserId(user.getId());

        //then
        assertThat(tasks).hasSize(2);
    }

    private User persistUser() {
        return entityManager.persistAndFlush(new User());
    }

    private Task persistTask(User user) {
        Task task = new Task();
        task.setUser(user);
        entityManager.persistAndFlush(task);
        return task;
    }

}