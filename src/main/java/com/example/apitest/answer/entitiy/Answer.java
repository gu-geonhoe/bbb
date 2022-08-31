package com.example.apitest.answer.entitiy;


import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ANSWERS")
public class Answer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;


    // user(1) : Answer(N)
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    //@JsonIgnore // 무한 반복 피하기 위해 @JsonIgnore 어노테이션을 추가하여 직렬화에서 제외시키는 방법
    private User user;
    //N인 Answer가 연관 관계 주인 -> User의 외래키인 userId를 관리한다. -> DB에 반영이 된다.

    private String userName;

    public void setUser(User user){
        this.user=user;
    }


    // question (1) : Answer(N)

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    //@JsonIgnore // 무한 반복 피하기 위해 @JsonIgnore 어노테이션을 추가하여 직렬화에서 제외시키는 방법
    private Question question;
    //N인 Answer가 연관 관계 주인

    public void setQuestion(Question question){this.question = question;}

    @Column(length = 500, nullable = false)
    private String content;

    //private long recommends;



}
