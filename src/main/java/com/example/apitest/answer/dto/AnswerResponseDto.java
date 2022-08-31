package com.example.apitest.answer.dto;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.audit.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    public void setUser(User user) {
        this.userId = user.getUserId();
    }
    public void setQuestion(Question question){
        this.questionId = question.getQuestionId();
    }
}
