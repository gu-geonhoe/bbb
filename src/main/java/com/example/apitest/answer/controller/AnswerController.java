package com.example.apitest.answer.controller;


import com.example.apitest.Question.dto.QuestionPatchDto;
import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.service.QuestionService;
import com.example.apitest.User.entity.User;
import com.example.apitest.User.service.UserService;
import com.example.apitest.answer.dto.AnswerPatchDto;
import com.example.apitest.answer.dto.AnswerPostDto;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.answer.mapper.AnswerMapper;
import com.example.apitest.answer.service.AnswerService;
import com.example.apitest.exception.BusinessLogicException;
import com.example.apitest.exception.ExceptionCode;
import com.example.apitest.response.MultiResponseDto;
import com.example.apitest.response.SingleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/answer")
@Validated
public class AnswerController {

    private final AnswerService answerService;
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerMapper mapper;

    private final int size = 10;

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
    @PostMapping("/{question-id}/reply")
    public ResponseEntity postAnswer( @PathVariable("question-id")long questionId,
                                        @Valid @RequestBody AnswerPostDto answerPostDto){
        User answerer = userService.findVerifiedUser(answerPostDto.getUserId());
        Question question = questionService.findQuestionByQuestionId(questionId);

        Answer answer = answerService.createAnswer(mapper.answerPostDtoToAnswer(answerPostDto,answerer,question));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponseDto(answer)),
                HttpStatus.CREATED);
    }

    //전체 답변 반환
    @GetMapping
    public ResponseEntity getAnswers(@Positive @RequestParam int page) {
        Page<Answer> pageAnswers = answerService.findAnswers(page - 1, size);
        List<Answer> answers = pageAnswers.getContent();


        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.answersToAnswerResponseDtos(answers), pageAnswers),
                HttpStatus.OK);

    }

    // 질문에 달린 전체 답변 반환
    @GetMapping("/aListByQuestionId/{question-id}")
    public ResponseEntity getAnswersByQuestionId(@PathVariable("question-id")long questionId,
                                                 @Positive @RequestParam int page){
        //questionId에 해당하는 Question 객체를 찾는다.
        Question question = questionService.findQuestionByQuestionId(questionId);

        Page<Answer> questionAnswers = answerService.searchQuestionAnswerList(question,page-1,size);
        List<Answer> answers = questionAnswers.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.answersToAnswerResponseDtos(answers),questionAnswers),
                HttpStatus.OK);
    }

    // 유저가 작성한 전체 답변 반환

    @GetMapping("/aListByUserId/{user-id}")
    public ResponseEntity getAnswersByUserId(@PathVariable("user-id")long userId,
                                                 @Positive @RequestParam int page){
        //questionId에 해당하는 Question 객체를 찾는다.
        User user = userService.findVerifiedUser(userId);

        Page<Answer> questionAnswers = answerService.searchUserAnswerList(user,page-1,size);
        List<Answer> answers = questionAnswers.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.answersToAnswerResponseDtos(answers),questionAnswers),
                HttpStatus.OK);
    }

    // 답변 조회 (answerId)
    @GetMapping("/{answer-id}")
    public ResponseEntity getAnswer(@PathVariable("answer-id") long answerId){
        Answer answer = answerService.findAnswer(answerId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponseDto(answer)),
                HttpStatus.OK);
    }
    // 답변 수정
    @PatchMapping("/edit/{question-id}/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("question-id") long questionId,
                                      @PathVariable("answer-id") long answerId,
                                      @RequestParam long userId,
                                      @Valid @RequestBody AnswerPatchDto answerPatchDto){



        answerPatchDto.setAnswerId(answerId);
        answerPatchDto.setUserId(userId);
        answerPatchDto.setQuestionId(questionId);


        //유효한 회원 Id인지 확인
        User editor = userService.findVerifiedUser(answerPatchDto.getUserId());
        //유효한 질문 ID인지 확인
        Question question = questionService.findQuestionByQuestionId(answerPatchDto.getQuestionId());
        Answer patchAnswer = mapper.answerPatchDtoToAnswer(answerPatchDto, editor, question);


        //원본 답변
        Answer selectedAnswer = answerService.findVerifiedAnswerId(answerId);

        if(!answerService.checkUserAuth(editor.getUserId(), selectedAnswer)){
            // 작성자와 요청한 유저Id가 같으면 true -> 다음 작업 수행
            throw new BusinessLogicException(ExceptionCode.NO_ACCESS);
        }
        Answer answer =
                answerService.updateAnswer(patchAnswer);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponseDto(answer))
                , HttpStatus.OK);
    }
    // 답변 삭제
    @DeleteMapping("/delete/{answer-id}")
    public ResponseEntity cancelAnswer(@PathVariable("answer-id") @Positive long answerId,
                                       @RequestParam @Positive long userId  ){

        //requestParam의 userId는 현재 로그인한 사용자의 userId
        User loginUser = userService.findVerifiedUser(userId);  // 현재 로그인한 사용자
        //삭제하려는 답변
        Answer selectedAnswer = answerService.findVerifiedAnswerId(answerId);

        // 답변 작성자와 현재 로그인한 사용자가 같은지 확인
        if(!answerService.checkUserAuth(loginUser.getUserId(), selectedAnswer)){
            // 작성자와 요청한 유저Id가 같으면 true
            throw new BusinessLogicException(ExceptionCode.NO_ACCESS);
        }

        answerService.cancelAnswer(answerId);
        return new ResponseEntity<>(
                "답변 삭제 완료"
                , HttpStatus.OK);
    }
}
