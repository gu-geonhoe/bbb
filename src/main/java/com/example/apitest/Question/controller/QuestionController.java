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
import java.util.List;

@RestController
@RequestMapping("/question")
@Validated
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final TagService tagService;

    private final QuestionMapper mapper;

    private final int size = 10;


    public QuestionController(QuestionService questionService,
                              QuestionMapper mapper,
                              UserService userService,
                              TagService tagService){
        this.questionService = questionService;
        this.mapper = mapper;
        this.userService = userService;
        this.tagService = tagService;
    }


    // 태그 적용 POST
    @PostMapping("/write") //글 작성 페이지에서 글 작성
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){
        //로그인 시 userid를 세션에 담고, questionPostDto에 넘겨주기 필요


        //questionPostDtoToQuestion 과정 전에 user 객체(writter)를 담는 과정 필요
        User writter = userService.findVerifiedUser(questionPostDto.getUserId());

        Question question = questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto, writter));



        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)),
                HttpStatus.CREATED);
    }
    @GetMapping //전체 질문 반환 , 응답 데이터에 questionTags 추가 되어야한다.
    public ResponseEntity getQuestions(@Positive @RequestParam int page) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, size);
        List<Question> questions = pageQuestions.getContent();

        // homework solution 추가
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.questionsToQuestionResponseDtos(questions), pageQuestions),
                HttpStatus.OK);

    }


    @GetMapping("/{question-id}")
    public ResponseEntity getQuestionByQuestionId(@PathVariable("question-id")long questionId){
        Question question = questionService.findQuestionByQuestionId(questionId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)),
                HttpStatus.OK);

    }

   @GetMapping("/qListByUserId/{user-id}") //회원이 작성한 글 조회 + 답변 및 댓글 정보 같이 반환
    public ResponseEntity getQuestionByUserId(@PathVariable("user-id")long userId,
                                              @Positive @RequestParam int page){

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
                                             @Positive @RequestParam int page){
        Tag tag = tagService.findVerifiedTag(tagId);


        //page
        Page<QuestionTag> questionTags = questionService.searchTagQuestionList(tag,page - 1, size);

        //data
        List<QuestionTag> questions = questionTags.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.questionTagsToQuestionTagResponseDtos(questions),questionTags),
                HttpStatus.OK);
    }



    @PatchMapping("/edit/{question-id}")   // question not found 문제
    public ResponseEntity patchQuestion(@PathVariable("question-id") long questionId, //@Positive 있었음
                                        @RequestParam long userId,  //@Positive 있었음
                                        @Valid @RequestBody QuestionPatchDto questionPatchDto){

        //글 수정 아이디 값 같은지 확인 해야함 question-id 값을 유저가 보유하는지 확인
        questionPatchDto.setUserId(userId);
        questionPatchDto.setQuestionId(questionId);

        //유효한 회원 Id인지 확인
        User editor = userService.findVerifiedUser(questionPatchDto.getUserId());

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

       //답변이 달리면 질문을 삭제할수 없도록 설정
       Question question = questionService.findQuestionByQuestionId(questionId);



       if (question.getAnswers().size() == 0) {  // 질문에 등록된 답변이 없을 때

           questionService.cancelQuestion(questionId);


           return new ResponseEntity<>(
                   "삭제 완료"
                   , HttpStatus.NO_CONTENT);

       } else
           return new ResponseEntity<>("삭제 실패",HttpStatus.FORBIDDEN);
   }
}
