package com.example.apitest.Question.controller;


import com.example.apitest.Question.dto.QuestionPatchDto;
import com.example.apitest.Question.dto.QuestionPostDto;
import com.example.apitest.Question.mapper.QuestionMapper;
import com.example.apitest.Question.service.QuestionService;
import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.User.service.UserService;
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
@RequestMapping("/question")
@Validated
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    private final QuestionMapper mapper;


    public QuestionController(QuestionService questionService,
                              QuestionMapper mapper,
                              UserService userService){
        this.questionService = questionService;
        this.mapper = mapper;
        this.userService = userService;
    }
/*
    @PostMapping("/write") //글 작성 페이지에서 글 작성
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){
        //uestion question = questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto));

        return new ResponseEntity<>(
        //        new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)),
                HttpStatus.CREATED);
    }
*/

    /*@PostMapping("/write/{user-id}") //글 작성 페이지에서 글 작성
    public ResponseEntity postQuestion(@PathVariable("user-id") long userId,
                                       @Valid @RequestBody QuestionPostDto questionPostDto){*/
    @PostMapping("/write") //글 작성 페이지에서 글 작성
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){
        //로그인 시 userid를 세션에 담고, questionPostDto에 넘겨주기 필요

        User writter = userService.findVerifiedUser(questionPostDto.getUserId());
        //questionPostDtoToQuestion 과정 전에 user 객체를 담는 과정 필요
        Question question = questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto,writter));

        return new ResponseEntity<>(
                       new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)),
                HttpStatus.CREATED);
    }


    @GetMapping //전체 질문 반환
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, size);
        List<Question> questions = pageQuestions.getContent();

        // homework solution 추가
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.questionsToQuestionResponseDtos(questions), pageQuestions),
                HttpStatus.OK);
    }


    @GetMapping("/{question-id}") //선택한 질문 조회 아이디 값 같은지 확인 해야함 question-id 값을 유저가 보유하는지 확인, + 답변 및 댓글 정보 같이 반환
    public ResponseEntity getQuestionByQuestionId(@PathVariable("question-id")long questionId){
        Question question = questionService.findQuestionByQuestionId(questionId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)),
                HttpStatus.OK);
    }

   @GetMapping("/qList/{user-id}") //회원이 작성한 글 조회 + 답변 및 댓글 정보 같이 반환
    public ResponseEntity getQuestionByUserId(@PathVariable("user-id")long userId,
                                              @Positive @RequestParam int page,
                                              @Positive @RequestParam int size){
        //page
        Page<Question> userQuestions = questionService.searchUserQuestionList(userId,page - 1, size);

        //data
        List<Question> questions = userQuestions.getContent();
        // homework solution 추가
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.questionsToQuestionResponseDtos(questions),userQuestions),
                HttpStatus.OK);
    }




    @PatchMapping("/edit/{question-id}") //글 수정 아이디 값 같은지 확인 해야함 question-id 값을 유저가 보유하는지 확인
    public ResponseEntity patchQuestion(@PathVariable("question-id") long questionId, //@Positive 있었음
                                        @RequestParam long userId,  //@Positive 있었음
                                        @Valid @RequestBody QuestionPatchDto questionPatchDto){

        questionPatchDto.setUserId(userId);
        questionPatchDto.setQuestionId(questionId);

        //유효한 회원 Id인지 확인
        User editor = userService.findVerifiedUser(questionPatchDto.getUserId());

        Question question =
                questionService.updateQuestion(mapper.questionPatchDtoToQuestion(questionPatchDto,editor));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question))
                , HttpStatus.OK);
    }

   @DeleteMapping("/delete/{question-id}")  //질문 삭제 아이디 값 같은지 확인 해야함 question-id 값을 유저가 보유하는지 확인

    public ResponseEntity cancelQuestion(@PathVariable("question-id") @Positive long questionId) {
        String result = questionService.cancelQuestion(questionId);

        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }

//    @PostMapping("/{question-id}/{answer-id}}")   //답변 작성
//    public ResponseEntity createAnswer(@RequestBody Answer answer){
//
//        Answer response = answerService.createAnswer(answer);
//        return new ResponseEntity<>(response,HttpStatus.CREATED);
//    }
/*
    @PostMapping("/{question-id}/comment")   //댓글 작성
    public ResponseEntity createComment(@RequestBody Answer answer){

        Answer response = answerService.createAnswer(answer);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{question-id}/comment/{comment-id}")  //댓글 수정
public ResponseEntity editComment(@RequestBody Answer answer){
        Answer response = answerService.createAnswer(answer);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}/comment/{comment-id}")  //댓글 삭제
public void deleteComment(){

    }
    */

}
