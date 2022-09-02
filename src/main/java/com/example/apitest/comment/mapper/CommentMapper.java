package com.example.apitest.comment.mapper;


import com.example.apitest.User.entity.User;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.comment.dto.CommentPatchDto;
import com.example.apitest.comment.dto.CommentPostDto;
import com.example.apitest.comment.dto.CommentResponseDto;
import com.example.apitest.comment.entity.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    //answerId 세팅 필요할지 여부 확인
    default Comment commentPostDtoToComment(CommentPostDto commentPostDto, User user, Answer answer,long questionId){
        Comment comment = new Comment();
        comment.setUser(user);

        comment.setUserName(user.getUserName());
        comment.setAnswer(answer);
        comment.setQuestionId(questionId);
        comment.setContent(commentPostDto.getContent());

        return comment;
    }

    default Comment commentPatchDtoToComment(CommentPatchDto commentPatchDto, User user, Answer answer){
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setAnswer(answer);
        comment.setCommentId(commentPatchDto.getCommentId());
        comment.setUserName(user.getUserName());
        comment.setQuestionId(commentPatchDto.getQuestionId());
        comment.setContent(commentPatchDto.getContent());

        return comment;
    }

    List<CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comments);

    default CommentResponseDto commentToCommentResponseDto(Comment comment){
        //quesitionId 추가하자
        CommentResponseDto commentResponseDto = new CommentResponseDto();

        commentResponseDto.setCommentId(comment.getCommentId());
        commentResponseDto.setUserName(comment.getUserName());
        commentResponseDto.setUserId(comment.getUser());
        commentResponseDto.setQuestionId(comment.getQuestionId());
        commentResponseDto.setAnswerId(comment.getAnswer());
        commentResponseDto.setContent(comment.getContent());
        commentResponseDto.setCreatedAt(comment.getCreatedAt());
        commentResponseDto.setModifiedAt(comment.getModifiedAt());

        return commentResponseDto;
    }

}
