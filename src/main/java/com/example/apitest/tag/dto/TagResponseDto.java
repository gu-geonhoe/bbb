package com.example.apitest.tag.dto;

import com.example.apitest.Question.entitiy.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
public class TagResponseDto {
    private long tagId;

    private String tagValue;
    private String tagInfo;

}
