package com.example.apitest.comment.dto;


import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.entitiy.QuestionTag;
import com.example.apitest.User.entity.User;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.audit.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentResponseDto extends Auditable {
    private long commentId;

    @Setter(AccessLevel.NONE)
    private long userId;
    private long questionId;
    private long answerId;


    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void setUserId(User user){this.userId = user.getUserId();}

    public void setAnswerId(Answer answer){this.answerId = answer.getAnswerId();}

    public void setQuestionId(long questionId) {this.questionId = questionId;}

}
