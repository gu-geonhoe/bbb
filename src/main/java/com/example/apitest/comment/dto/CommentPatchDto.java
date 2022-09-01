package com.example.apitest.comment.dto;

import com.example.apitest.audit.Auditable;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
public class CommentPatchDto  {

    private long commentId;
    private long answerId;
    private long userId;
    private long questionId;

    @NotBlank(message = "공백 불가")
    private String content;



    public void setCommentId(long commentId){this.commentId = commentId;}

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
