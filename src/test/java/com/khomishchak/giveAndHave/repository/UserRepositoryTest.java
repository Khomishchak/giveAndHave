package com.khomishchak.giveAndHave.repository;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void init() {
        entityManager.clear();
    }

    @Test
    void shouldReturnReceiverAndSenderOfTransaction() {
        //given
        Transaction transaction = persistTransaction();

        User receiver = persistUser(transaction);
        User sender = persistUser(transaction);

        //when
        List<Long> users = userRepository.findSenderAndReceiver(transaction.getId());

        //then
        assertThat(users).isEqualTo(List.of(receiver.getId(), sender.getId()));
    }

    private Transaction persistTransaction() {
        return entityManager.persistAndFlush(new Transaction());
    }

    private User persistUser (Transaction transaction) {
        User user = new User();
        user.getTransactions().add(transaction);
        entityManager.persistAndFlush(user);

        return user;
    }
}