package com.example.apitest.Question.controller;


import com.example.apitest.Question.dto.QuestionPatchDto;
import com.example.apitest.Question.dto.QuestionPostDto;
import com.example.apitest.Question.entitiy.QuestionTag;
import com.example.apitest.Question.mapper.QuestionMapper;
import com.example.apitest.Question.service.QuestionService;
import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.User.service.UserService;
import com.example.apitest.response.MultiResponseDto;
import com.example.apitest.response.SingleResponseDto;
import com.example.apitest.tag.entity.Tag;
import com.example.apitest.tag.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
@Validated
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final TagService tagService;

    private final QuestionMapper mapper;


    public QuestionController(QuestionService questionService,
                              QuestionMapper mapper,
                              UserService userService,
                              TagService tagService){
        this.questionService = questionService;
        this.mapper = mapper;
        this.userService = userService;
        this.tagService = tagService;
    }

    // 태그 적용 전 POST
   /* @PostMapping("/write") //글 작성 페이지에서 글 작성
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){
        //로그인 시 userid를 세션에 담고, questionPostDto에 넘겨주기 필요

        User writter = userService.findVerifiedUser(questionPostDto.getUserId());
        //questionPostDtoToQuestion 과정 전에 user 객체를 담는 과정 필요
        Question question = questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto,writter));

        return new ResponseEntity<>(
                       new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)),
                HttpStatus.CREATED);
    }*/


    // 태그 적용 POST
    @PostMapping("/write") //글 작성 페이지에서 글 작성
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){
        //로그인 시 userid를 세션에 담고, questionPostDto에 넘겨주기 필요

        /*// tagValue 리스트를 입력 받아, 입력 받은 수의 크기를 가지는 Tag 타입의 List 생성
        List<Tag> tags = new ArrayList<>();
        for(String tagValue : questionPostDto.getTagValues())
            tags.add(tagService.findTagByTagValue(tagValue));*/


        //questionPostDtoToQuestion 과정 전에 user 객체(writter)를 담는 과정 필요
        User writter = userService.findVerifiedUser(questionPostDto.getUserId());

        Question question = questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto, writter));

        //추가한 부분 : 각 tag에 현재 question 객체 담기
        //for(Tag tag : tags)
        //    tag.setQuestion(question);
        // -> Question 내부에 tag 존재하고, tag 내부에 question 존재하기 때문에 무한 반복된다.

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

   @GetMapping("/qListByUserId/{user-id}") //회원이 작성한 글 조회 + 답변 및 댓글 정보 같이 반환
    public ResponseEntity getQuestionByUserId(@PathVariable("user-id")long userId,
                                              @Positive @RequestParam int page,
                                              @Positive @RequestParam int size){

        //입력 받은 userId를 가지는 User 객체를 찾는다.
        User user = userService.findUser(userId);
        //page
        Page<Question> userQuestions = questionService.searchUserQuestionList(user,page - 1, size);

        //data
        List<Question> questions = userQuestions.getContent();
        // homework solution 추가
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.questionsToQuestionResponseDtos(questions),userQuestions),
                HttpStatus.OK);
    }

    @GetMapping("/qListByTagId/{tag-id}")
    public ResponseEntity getQuestionBytagId(@PathVariable("tag-id")long tagId,
                                             @Positive @RequestParam int page,
                                             @Positive @RequestParam int size){
        Tag tag = tagService.findVerifiedTag(tagId);

        //question의 태그 리스트인 tags에 찾으려는 tag가 있는지 확인
        // 해당 태그를 가진 모든 question을 불러오자!


        //page, 질문마다 가 존재 -> 지정한 tag를 가지는 모든 질문을 찾아서 tagQuestions에 담는다.
        Page<QuestionTag> questionTags = questionService.searchTagQuestionList(tag,page - 1, size);
        //Question 태그 내부에 question을 가져와야 한다!!!!!!!!!!!!!!!!!

        //data
        List<QuestionTag> questions = questionTags.getContent();
        // homework solution 추가
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.questionTagsToQuestionTagResponseDtos(questions),questionTags),
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

        /*// tagValue 리스트를 입력 받아, 입력 받은 수의 크기를 가지는 Tag 타입의 List 생성
        List<Tag> tags = new ArrayList<>();
        for(String tagValue : questionPatchDto.getTagValues())
            tags.add(tagService.findTagByTagValue(tagValue));*/

        Question question =
                questionService.updateQuestion(mapper.questionPatchDtoToQuestion(questionPatchDto, editor));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question))
                , HttpStatus.OK);
    }

   @DeleteMapping("/delete/{question-id}")  //질문 삭제 아이디 값 같은지 확인 해야함 question-id 값을 유저가 보유하는지 확인

   //질문 삭제할 때 question(1) - answer (n) 중 answer이 존재하는지 확인
   // 존재하면 삭제 x, 없다면 삭제
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
