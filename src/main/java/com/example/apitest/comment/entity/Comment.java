package com.example.apitest.comment.entity;

import com.example.apitest.User.entity.User;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    private long questionId;

    // user(1) : Comment(N)
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonIgnore // 무한 반복 피하기 위해 @JsonIgnore 어노테이션을 추가하여 직렬화에서 제외시키는 방법
    private User user;
    //N인 Answer가 연관 관계 주인 -> User의 외래키인 userId를 관리한다. -> DB에 반영이 된다.


    private String userName;

    public void setUser(User user){
        this.user=user;
    }

    // answer(1) : Comment(N)
    @ManyToOne
    @JoinColumn(name = "ANSWER_ID")
    @JsonIgnore
    private Answer answer;



    public void setAnswer(Answer answer){this.answer = answer;}

    @Column(length = 500, nullable = false)
    private String content;

    /*
    답변이랑 같은 구조 : content만 작성
    기본키 : commentId
    content
    외래키 : answerId, userId
    작성일자,수정일자는 dto에서 추가
     */
}
