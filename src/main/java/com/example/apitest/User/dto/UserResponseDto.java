package com.example.apitest.User.dto;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    private long userId;
    private String userName;
    private String email;
    private String password;


    private List<Question> questions;
    private List<Answer> answers;
    private List<Comment> comments;
}
