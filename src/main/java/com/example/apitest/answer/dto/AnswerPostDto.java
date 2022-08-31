package com.example.apitest.answer.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class AnswerPostDto {

    @Positive
    private long userId;

    @NotBlank(message = "공백 불가")
    private String content;

    public void setUserId(long userId){this.userId = userId;}

    /*public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }*/
}
