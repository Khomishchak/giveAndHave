package com.khomishchak.giveAndHave.repository;

import com.khomishchak.giveAndHave.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query(value = "SELECT id FROM tasks t " +
            "WHERE t.user_id = :userId", nativeQuery = true)
    List<Long> findTaskIdByUserId(@Param("userId") Long userId);

}