package com.example.apitest.Question.dto;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.tag.entity.Tag;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
public class QuestionPostDto {

    @Positive
    private long userId;

    @NotBlank(message = "공백 불가")
    private String questionTitle;

    @NotBlank(message = "공백 불가")
    private String content;

    private List<QuestionTagDto> questionTags;
    /*
    "questionTags": [
                        {"tagId": 1,"tagValue" : "잘못된 태그명"},
                        {"tagId": 2, "tagValue" : "잘못된 태그정보"}
     ] <-- questionpostdto에서 입력 받고
           questionMapper로 이동
     */


  /*  public User getUser(){
        User user = new User();
        user.setUserId(userId);
        return user;
    }*/

    public void setUserId(long userId){
        this.userId = userId;
    }  // 컨트롤러에서 받은 userid를 postdto의 userid에 대입한다.
    /*
    public User getUSer(){
        User user = new User();
        user.setUserId(userId);
        return user;
    }
     */

}
