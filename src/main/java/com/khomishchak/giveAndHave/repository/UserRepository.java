package com.khomishchak.giveAndHave.repository;

import com.khomishchak.giveAndHave.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    @Query(value = "SELECT user_id FROM transaction_users tu " +
            "WHERE tu.transaction_id = :transactionId", nativeQuery = true)
    List<Long> findSenderAndReceiver(@Param("transactionId") Long transactionId);
}
