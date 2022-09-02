package com.example.apitest.answer.service;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.service.QuestionService;
import com.example.apitest.User.entity.User;
import com.example.apitest.User.service.UserService;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.answer.repository.AnswerRepository;
import com.example.apitest.exception.BusinessLogicException;
import com.example.apitest.exception.ExceptionCode;
import com.example.apitest.specification.AnswerSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {

    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerRepository answerRepository;

    public AnswerService(UserService userService,
                         QuestionService questionService,
                         AnswerRepository answerRepository){
        this.userService = userService;
        this.questionService = questionService;
        this.answerRepository = answerRepository;
    }

    // 답변 생성
    public Answer createAnswer(Answer answer){

        verifyAnswer(answer);

        Answer savedAnswer = saveAnswer(answer);
        return savedAnswer;
    }

    public void verifyAnswer(Answer answer) {
        //  회원 존재 여부 확인
        userService.findVerifiedUser(answer.getUser().getUserId());
        // 질문 존재 여부 확인
        questionService.findQuestionByQuestionId(answer.getQuestion().getQuestionId());

    }
    public Answer updateAnswer(Answer answer){
        Answer findAnswer = findVerifiedAnswerId(answer.getAnswerId());

        //답변 내용 변경
        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));

        return saveAnswer(findAnswer);
    }

    private Answer saveAnswer(Answer answer){
        return answerRepository.save(answer);
    }

    public Answer findAnswer(long answerId){
        return findVerifiedAnswerId(answerId);
    }
    public Answer findVerifiedAnswerId(long answerId){  // 답변 수정 시 문제 발생
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer =
            optionalAnswer.orElseThrow(()->
                    new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        return findAnswer;
    }

    //전체 답변 단순 조회
    public Page<Answer> findAnswers(int page, int size){
        return answerRepository.findAll(PageRequest.of(page, size,
                Sort.by("answerId").descending()));
    }

    // 질문에 달린 전체 답변 조회
    public Page<Answer> searchQuestionAnswerList(Question question, int page, int size){

        Specification<Answer> spec = Specification.where(AnswerSpecification.equalQuestion(question));
        return answerRepository.findAll(spec, PageRequest.of(page,size,
                Sort.by("answerId").descending()));
    }

    // 회원이 작성한 전체 답변 조회
    public Page<Answer> searchUserAnswerList(User user, int page, int size){

        Specification<Answer> spec = Specification.where(AnswerSpecification.equalUser(user));
        return answerRepository.findAll(spec, PageRequest.of(page,size,
                Sort.by("answerId").descending()));
    }

    public void cancelAnswer(long answerId){
        Answer findAnswer = findVerifiedAnswerId(answerId);

        answerRepository.delete(findAnswer);
    }

    public boolean checkUserAuth(long userId,Answer answer){
        boolean result = false;

        if(userId == answer.getUser().getUserId()){
            result = true;
        }
        return result;
    }
}
