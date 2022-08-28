package com.example.apitest.Question.dto;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class QuestionPostDto {

    @Positive
    private long userId;

    @NotBlank(message = "공백 불가")
    private String questionTitle;

    @NotBlank(message = "공백 불가")
    private String content;


  /*  public User getUser(){
        User user = new User();
        user.setUserId(userId);
        return user;
    }*/

    public void setUserId(long userId){
        this.userId = userId;
    }  // 컨트롤러에서 받은 userid를 postdto의 userid에 대입한다.

}
