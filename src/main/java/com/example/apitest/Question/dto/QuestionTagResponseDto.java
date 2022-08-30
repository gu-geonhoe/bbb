package com.example.apitest.Question.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QuestionTagResponseDto {
    private long tagId;
    private String tagValue;
    private String tagInfo;
    private long questionId;
    private String questionTitle;
    private String  userName;

}
