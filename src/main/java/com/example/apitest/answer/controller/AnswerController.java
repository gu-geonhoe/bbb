package com.example.apitest.answer.controller;


import com.example.apitest.Question.service.QuestionService;
import com.example.apitest.User.service.UserService;
import com.example.apitest.answer.mapper.AnswerMapper;
import com.example.apitest.answer.service.AnswerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
@Validated
public class AnswerController {

    private final AnswerService answerService;
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerMapper mapper;

    public AnswerController(AnswerService answerService,
                            UserService userService,
                            QuestionService questionService,
                            AnswerMapper mapper){
        this.answerService = answerService;
        this.userService = userService;
        this.questionService = questionService;
        this.mapper = mapper;
    }

    // 답변 작성

    // 질문에 달린 전체 답변 반환

    // 유저가 작성한 전체 답변 반환

    // 답변 조회

    // 답변 수정

    // 답변 삭제
}
