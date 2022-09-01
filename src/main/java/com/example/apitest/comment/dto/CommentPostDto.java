package com.example.apitest.comment.dto;

import com.example.apitest.audit.Auditable;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
public class CommentPostDto {



    private long userId;

    @NotBlank(message = "공백 불가")
    private String content;


    public void setUserId(long userId){this.userId = userId;}
}
