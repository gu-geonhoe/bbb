package com.example.apitest.answer.dto;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.audit.Auditable;
import com.example.apitest.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AnswerResponseDto extends Auditable {
    private long answerId;
    @Setter(AccessLevel.NONE)
    private long userId;
    private long questionId;

    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<Comment> comments;

    public void setUser(User user) {
        this.userId = user.getUserId();
    }
    public void setQuestion(Question question){
        this.questionId = question.getQuestionId();
    }
}
