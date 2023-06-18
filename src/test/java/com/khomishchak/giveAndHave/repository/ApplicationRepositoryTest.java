package com.khomishchak.giveAndHave.repository;

import com.khomishchak.giveAndHave.model.Application;
import com.khomishchak.giveAndHave.model.Task;
import com.khomishchak.giveAndHave.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager.clear();
    }

    @Test
    void shouldReturnCorrectNewMessageCountForUser() {
        // given
        User user = persistUser();
        persistApplication(user);
        persistApplication(user);

        // when
        int newMessage = applicationRepository.findNewMessagesAmount(user.getId());

        //then
        assertThat(newMessage).isEqualTo(2);
    }

    @Test
    void shouldReturnApplicationsForUser() {
        // given
        User user = persistUser();

        persistApplication(user);
        persistApplication(user);
        persistApplication(persistUser());

        //when
        List<Application> givenResult = applicationRepository.findApplicationsByUserId(user.getId());

        //then
        assertThat(givenResult).hasSize(2);
    }

    @Test
    void shouldReturnEmptyOptionalForNonexistentApplication() {
        //when
        Optional<Application> emptyApplication = applicationRepository
                .findByUserIdAndTaskId(2L, 2L);

        //then
        assertThat(emptyApplication).isEmpty();
    }

    @Test
    void shouldReturnOptionalForExistentApplication() {
        //given
        User user = persistUser();
        Task task = persistTask();

        persistApplication(user, task);

        //when
        Optional<Application> notEmptyApplication = applicationRepository
                .findByUserIdAndTaskId(user.getId(), task.getId());

        //then
        assertThat(notEmptyApplication).isNotEmpty();
    }

    private User persistUser() {
        return entityManager.persistAndFlush(new User());
    }

    private Task persistTask() {
        return entityManager.persistAndFlush(new Task());
    }

    private Application persistApplication(User user) {
        Application application = new Application();
        application.setUser(user);
        entityManager.persistAndFlush(application);
        return application;
    }

    private Application persistApplication(User user, Task task) {
        Application application = new Application();
        application.setUser(user);
        application.setTask(task);
        entityManager.persistAndFlush(application);
        return application;
    }
}