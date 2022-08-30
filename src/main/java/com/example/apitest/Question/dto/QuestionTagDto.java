package com.example.apitest.Question.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class QuestionTagDto {
    @Positive
    private long tagId;
    private String tagValue;

    private long questionId;
    private String questionTitle;
    private String userName;


}
