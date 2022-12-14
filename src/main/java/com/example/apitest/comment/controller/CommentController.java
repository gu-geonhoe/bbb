package com.example.apitest.comment.controller;


import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.service.QuestionService;
import com.example.apitest.User.entity.User;
import com.example.apitest.User.service.UserService;
import com.example.apitest.answer.dto.AnswerPatchDto;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.answer.service.AnswerService;
import com.example.apitest.comment.dto.CommentPatchDto;
import com.example.apitest.comment.dto.CommentPostDto;
import com.example.apitest.comment.entity.Comment;
import com.example.apitest.comment.mapper.CommentMapper;
import com.example.apitest.comment.service.CommentService;
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
@RequestMapping("/comment")
@Validated
public class CommentController {

    private final AnswerService answerService;
    private final UserService userService;
    private final QuestionService questionService;
    private final CommentService commentService;
    private final CommentMapper mapper;

    private final int size = 10;

    public CommentController(AnswerService answerService,
                             UserService userService,
                             QuestionService questionService,
                             CommentService commentService,
                             CommentMapper mapper){
        this.answerService = answerService;
        this.userService = userService;
        this.questionService = questionService;
        this.commentService = commentService;
        this.mapper = mapper;
    }

    //?????? ??????
    @PostMapping("/postcomment/{question-id}/{answer-id}/{user-id}")
    public ResponseEntity postComment(@PathVariable("question-id") long questionId,
                                      @PathVariable("answer-id") long answerId,
                                      @PathVariable("user-id") long userId,
                                      @Valid @RequestBody CommentPostDto commentPostDto){


        User user = userService.findVerifiedUser(userId);
        Answer answer = answerService.findVerifiedAnswerId(answerId);


        Comment comment = commentService.crerteComment(mapper.commentPostDtoToComment(commentPostDto,user,answer,questionId));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.commentToCommentResponseDto(comment)),
                HttpStatus.CREATED);
    }
    //?????? ?????? ??????
    @GetMapping
    public ResponseEntity getComments(@Positive @RequestParam int page) {

        Page<Comment> pageComments = commentService.findComments(page - 1, size);
        List<Comment> comments = pageComments.getContent();


        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.commentsToCommentResponseDtos(comments), pageComments),
                HttpStatus.OK);
    }
    //????????? ????????? ?????? ?????? ??????
    @GetMapping("/cListByUserId/{user-id}")
    public ResponseEntity getCommentsByUserId(@PathVariable("user-id")long userId,
                                                 @Positive @RequestParam int page){
        User user = userService.findVerifiedUser(userId);
        Page<Comment> pageComments = commentService.searchUserCommentList(user,page - 1, size);
        List<Comment> comments = pageComments.getContent();


        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.commentsToCommentResponseDtos(comments), pageComments),
                HttpStatus.OK);

    }

    //????????? ?????? ?????? ?????? ??????
    @GetMapping("/cListByAnswerId/{answer-id}")
    public ResponseEntity getCommentsByAnswerId(@PathVariable("answer-id")long answerId,
                                                 @Positive @RequestParam int page){
       Answer answer = answerService.findVerifiedAnswerId(answerId);
        Page<Comment> pageComments = commentService.searchAnswerCommentList(answer,page - 1, size);
        List<Comment> comments = pageComments.getContent();


        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.commentsToCommentResponseDtos(comments), pageComments),
                HttpStatus.OK);

    }
    //?????? ?????? ??????
    @GetMapping("/{comment-id}")
    public ResponseEntity getAnswer(@PathVariable("comment-id") long commentId){
        Comment comment = commentService.findComment(commentId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.commentToCommentResponseDto(comment)),
                HttpStatus.OK);
    }
    //?????? ??????
    @PatchMapping("/edit/{question-id}/{answer-id}/{comment-id}")
    public ResponseEntity patchAnswer(@PathVariable("question-id") long questionId,
                                      @PathVariable("answer-id") long answerId,
                                      @PathVariable("comment-id") long commentId,
                                      @RequestParam long userId,
                                      @Valid @RequestBody CommentPatchDto commentPatchDto){


        commentPatchDto.setCommentId(commentId);
        commentPatchDto.setAnswerId(answerId);
        commentPatchDto.setUserId(userId);
        commentPatchDto.setQuestionId(questionId);


        //????????? ?????? Id?????? ??????
        User editor = userService.findVerifiedUser(commentPatchDto.getUserId());

        //????????? ???????????? ??????
        Answer answer = answerService.findVerifiedAnswerId(commentPatchDto.getAnswerId());
        Comment patchComment = mapper.commentPatchDtoToComment(commentPatchDto, editor, answer);

        //?????? ??????
        Comment selectedComment = commentService.findComment(commentId);

        if(!commentService.checkUserAuth(editor.getUserId(), selectedComment)){
            // ???????????? ????????? ??????Id??? ????????? true -> ?????? ?????? ??????
            throw new BusinessLogicException(ExceptionCode.NO_ACCESS);
        }

        Comment comment =
                commentService.updateComment(patchComment);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.commentToCommentResponseDto(comment))
                , HttpStatus.OK);

    }
    //?????? ??????
    @DeleteMapping("/delete/{question-id}/{answer-id}/{comment-id}")
    public ResponseEntity cancelAnswer(@PathVariable("question-id") long questionId,
                                       @PathVariable("answer-id") long answerId,
                                       @PathVariable("comment-id") long commentId,
                                       @RequestParam long userId){
        //requestParam??? userId??? ?????? ???????????? ???????????? userId
        User loginUser = userService.findVerifiedUser(userId);  // ?????? ???????????? ?????????
        //??????????????? ??????
        Comment selectedComment = commentService.findVerifiedCommentId(commentId);

        // ?????? ???????????? ?????? ???????????? ???????????? ????????? ??????
        if(!commentService.checkUserAuth(loginUser.getUserId(), selectedComment)){
            // ???????????? ????????? ??????Id??? ????????? true
            throw new BusinessLogicException(ExceptionCode.NO_ACCESS);
        }


        commentService.cancelComment(commentId);

        return new ResponseEntity<>(
                "?????? ?????? ??????"
                , HttpStatus.OK);

    }



}