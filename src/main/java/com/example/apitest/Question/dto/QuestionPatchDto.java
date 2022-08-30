package com.example.apitest.Question.dto;

import com.example.apitest.Question.entitiy.Question;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
public class QuestionPatchDto {

    private long questionId;

    private long userId;

  // 질문 상태 변경 기능 추가
    @NotBlank(message = "공백 불가")
    private String questionTitle;

    @NotBlank(message = "공백 불가")
    private String content;

   /* private List<String> tagValues = new ArrayList<>();*/
    // 질문에 해당하는 tagValue(ex. JAVA)를 선택


    private List<QuestionTagDto> questionTags;
  /*  public User getUser(){
        User user = new User();
        user.setUserId(userId);
        return user;
    }*/

    public void setUserId(long userId){
        this.userId = userId;
    }  // 컨트롤러에서 받은 userid를 patchdto의 userid에 대입한다.

    public void setQuestionId(long questionId){
        this.questionId = questionId;
    }


}
