package com.triviamachine.triviamachine.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface QuestionScheduleRepository extends JpaRepository<QuestionSchedule, Long> {


    QuestionSchedule findByDate(Date date);
}
