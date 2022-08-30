package com.example.apitest.Question.dto;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.audit.Auditable;
import com.example.apitest.tag.entity.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class QuestionResponseDto extends Auditable {
  private long questionId;

  @Setter(AccessLevel.NONE)
  private long userId;
  private String userName;
  private String questionTitle;
  private Question.QuestionStatus questionStatus;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  /*private List<Tag> tags = new ArrayList<>();*/
 // 태그의 전체 정보가 아닌 태그명이나 태그 아이디만 뽑아내는 작업 필요

  private List<QuestionTagResponseDto> questionTags;

  public void setUser(User user){
      this.userId = user.getUserId();
  }

  /*public void setTags(List<Tag> tags) {
    this.tags = tags;
  }*/
}
