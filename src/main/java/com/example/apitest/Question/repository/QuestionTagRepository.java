package com.example.apitest.Question.repository;

import com.example.apitest.Question.entitiy.QuestionTag;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long>, JpaSpecificationExecutor<QuestionTag> {

/*    Optional<QuestionTag> findByQuestionId(long questionId);*/
      /*Optional<QuestionTag> findByQuestion(Specification<QuestionTag> question);*/
}
