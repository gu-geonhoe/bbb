package com.example.apitest.Question.dto;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.audit.Auditable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

public class QuestionResponseDto extends Auditable {
  private long questionId;

  @Setter(AccessLevel.NONE)
  private long userId;
  private String questionTitle;
  private Question.QuestionStatus questionStatus;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;


  public void setUser(User user){
      this.userId = user.getUserId();
  }
}
