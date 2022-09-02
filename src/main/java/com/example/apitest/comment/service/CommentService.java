package com.example.apitest.comment.service;


import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.service.QuestionService;
import com.example.apitest.User.entity.User;
import com.example.apitest.User.service.UserService;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.answer.service.AnswerService;
import com.example.apitest.comment.entity.Comment;
import com.example.apitest.comment.repository.CommentRepository;
import com.example.apitest.exception.BusinessLogicException;
import com.example.apitest.exception.ExceptionCode;
import com.example.apitest.specification.AnswerSpecification;
import com.example.apitest.specification.CommentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CommentRepository commentRepository;

    public CommentService(UserService userService,
                          QuestionService questionService,
                          AnswerService answerService,
                          CommentRepository commentRepository){
        this.userService = userService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.commentRepository = commentRepository;
    }

    public Comment crerteComment(Comment comment){

        verifyComment(comment);
        Comment savedComment = saveComment(comment);
        return savedComment;
    }

    public void verifyComment(Comment comment) {
        //  회원 존재 여부 확인
        userService.findVerifiedUser(comment.getUser().getUserId());

        //질문 존재 여부 확인
        questionService.findQuestionByQuestionId(comment.getAnswer().getQuestion().getQuestionId());

        // 답변 존재 여부 확인
        questionService.findQuestionByQuestionId(comment.getAnswer().getAnswerId());

    }

    public Comment updateComment(Comment comment){
        Comment findComment = findVerifiedCommentId(comment.getCommentId());

        //답변 내용 변경
        Optional.ofNullable(comment.getContent())
                .ifPresent(content -> findComment.setContent(content));

        return saveComment(findComment);
    }

    public Comment findComment(long commentId){return findVerifiedCommentId(commentId);}

    public Comment findVerifiedCommentId(long commentId){  // 답변 수정 시 문제 발생
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }

    private Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }


    //전체 댓글 단순 조회
    public Page<Comment> findComments(int page, int size){
        return commentRepository.findAll(PageRequest.of(page, size,
                Sort.by("commentId").descending()));
    }

    //유저가 작성한 전체 댓글 조회
    public Page<Comment> searchUserCommentList(User user, int page, int size){

        Specification<Comment> spec = Specification.where(CommentSpecification.equalUser(user));
        return commentRepository.findAll(spec, PageRequest.of(page,size,
                Sort.by("commentId").descending()));
    }

    //답변에 달린 전체 댓글 조회
    public Page<Comment> searchAnswerCommentList(Answer answer, int page, int size){

        Specification<Comment> spec = Specification.where(CommentSpecification.equalAnswer(answer));
        return commentRepository.findAll(spec, PageRequest.of(page,size,
                Sort.by("commentId").descending()));
    }

    //댓글 삭제
    public void cancelComment(long commentId){
        Comment findComment = findVerifiedCommentId(commentId);

        commentRepository.delete(findComment);
    }

    public boolean checkUserAuth(long userId,Comment comment){
        boolean result = false;

        if(userId == comment.getUser().getUserId()){
            result = true;
        }
        return result;
    }



}
