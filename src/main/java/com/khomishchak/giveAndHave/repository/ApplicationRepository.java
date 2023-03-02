package com.khomishchak.giveAndHave.repository;

import com.khomishchak.giveAndHave.model.Application;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {


    @Query(value = "SELECT COUNT(a.id) FROM applications a " +
            "WHERE a.task_id IN (" +
            "SELECT t.id FROM tasks t " +
            "WHERE t.user_id = :userId)" +
            "AND a.status IS NULL", nativeQuery = true)
    public int findNewMessagesAmount(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM applications a " +
        "WHERE a.task_id IN (" +
	    "SELECT t.id FROM tasks t " +
	    "WHERE t.user_id = :userId)", nativeQuery = true)
    public List<Application> findApplicationsByUserId(@Param("userId") Long userId);

    public List<Application> findAllByTaskId(Long taskId);

    @Query(value = "SELECT * FROM applications a WHERE a.user_id = :userId AND a.task_id = :taskId", nativeQuery = true)
    public Optional<Application> findByUserIdAndTaskId(@Param("userId") Long userId, @Param("taskId")Long taskId);
}
