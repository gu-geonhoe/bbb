package com.example.apitest.answer.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AnswerPatchDto {

    private long answerId;
    private long userId;
    private long questionId;

    @NotBlank(message = "공백 불가")
    private String content;

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
}
