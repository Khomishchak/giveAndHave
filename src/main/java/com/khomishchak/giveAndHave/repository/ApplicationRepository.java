package com.khomishchak.giveAndHave.repository;

import com.khomishchak.giveAndHave.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
