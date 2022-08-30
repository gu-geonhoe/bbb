package com.example.apitest.Question.repository;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.entitiy.QuestionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long>, JpaSpecificationExecutor<QuestionTag> {
}
