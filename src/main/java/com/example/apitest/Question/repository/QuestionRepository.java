package com.example.apitest.Question.repository;

import com.example.apitest.Question.entitiy.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {


    //Specification을 인자로 받는 findAll 함수를 사용하기 위해서는 인터페이스에 JpaSpecificationExecutor를 상속받아야 한다.


    /*Optional<Question> findByQuestionTitle(String questionTitle);  // 제목으로 질문글 찾기
    Optional<Question> findByViews(long views);          //조회수로 질문글 찾기  --> 정렬에 활용
    Optional<Question> findByRecommends(long recommends); //추천수로 질문글 찾기  --> 정렬에 활용*/
}


