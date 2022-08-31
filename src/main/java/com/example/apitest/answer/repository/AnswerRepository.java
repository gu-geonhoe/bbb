package com.example.apitest.answer.repository;

import com.example.apitest.answer.entitiy.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
