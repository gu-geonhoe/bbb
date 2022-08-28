package com.example.apitest.Question.service;


import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.repository.QuestionRepository;

import com.example.apitest.QuestionSpecification;
import com.example.apitest.User.repository.UserRepository;
import com.example.apitest.User.service.UserService;
import com.example.apitest.exception.BusinessLogicException;
import com.example.apitest.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final UserService userService;
    private final QuestionRepository questionRepository;
    //private final AnswerService answerService;

    //AnswerService 추가
    //commentService 추가

    public QuestionService(UserService userService,
                           QuestionRepository questionRepository
                           ){
        this.userService = userService;
        this.questionRepository = questionRepository;
        //this.answerService = answerService;

    }

    public Question createQuestion(Question question){

        verifyQuestion(question);

        Question savedQuestion = saveQuestion(question);
        return savedQuestion;


    }             //// create까지 적용


    private void verifyQuestion(Question question) {
        // 회원이 존재하는지 확인
        userService.findVerifiedUser(question.getUser().getUserId());

       /* // 답글, 댓글이 존재하는지 확인
        question.getTotalEntities().stream()
                .forEach(totalEntity -> answerService.
                        findVerifiedAnswer(totalEntity.getAnswer().getAnswerId()));*/
    }
   public Question updateQuestion(Question question) {
        Question findQuestion = findVerifiedQuestionId(question.getQuestionId());
        //ofNullable은 일반 객체뿐만 아니라 null값까지 입력으로 받을 수 있다
       //isPresent 메서드로 현재 Optional이 보유한 값이 null인지 아닌지를 확인할 수 있습니다.
       //출처: https://engkimbs.tistory.com/646 [새로비:티스토리]

        //질문 상태 변경
        Optional.ofNullable(question.getQuestionStatus())
                .ifPresent(questionStatus -> findQuestion.setQuestionStatus(questionStatus));
        //질문 제목 변경
       Optional.ofNullable(question.getQuestionTitle())
               .ifPresent(questionTitle -> findQuestion.setQuestionTitle(questionTitle));
       //질문 내용 변경
       Optional.ofNullable(question.getContent())
               .ifPresent(content -> findQuestion.setContent(content));

        return saveQuestion(findQuestion);
   }

    private Question findVerifiedQuestionId(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }

    /*private Question findVerifiedQuestionId(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findQuestion;
    }*/

    private Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question findQuestionByQuestionId(long questionId) {
        return findVerifiedQuestionId(questionId);
    }

    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size,
                Sort.by("questionId").descending()));
    }


    public Page<Question> searchUserQuestionList(Long userId, int page, int size) {

        Specification<Question> spec = Specification.where(QuestionSpecification.equalUserId(userId));
        Page<Question> userQuestionsList = questionRepository.findAll(PageRequest.of(page,size,
                Sort.by("createdAt").descending()));
        return userQuestionsList;
    }

    public String cancelQuestion(long questionId) {
        Question findQuestion = findVerifiedQuestionId(questionId);
       /* long deletedQuestionId = findQuestion.getQuestionId();*/

        /*findQuestion.setQuestionStatus(Question.QuestionStatus.QUESTION_CANCEL);*/

        questionRepository.delete(findQuestion);
        return "글이 삭제되었습니다.";
    }




}
