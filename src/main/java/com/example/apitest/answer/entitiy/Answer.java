package com.example.apitest.answer.entitiy;


import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.audit.Auditable;
import com.example.apitest.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;


    // user(1) : Answer(N)
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // 무한 반복 피하기 위해 @JsonIgnore 어노테이션을 추가하여 직렬화에서 제외시키는 방법
    private User user;
    //N인 Answer가 연관 관계 주인 -> User의 외래키인 userId를 관리한다. -> DB에 반영이 된다.

    private String userName;

    public void setUser(User user){
        this.user=user;
    }


    // question (1) : Answer(N)

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore // 무한 반복 피하기 위해 @JsonIgnore 어노테이션을 추가하여 직렬화에서 제외시키는 방법
    private Question question;
    //N인 Answer가 연관 관계 주인

    public void setQuestion(Question question){this.question = question;}

    // answer (1 ) : comment(n)

    @OneToMany(mappedBy = "answers", cascade = CascadeType.PERSIST) //REMOVE  PERSIST ALL
    private List<Comment> comments = new ArrayList<>();

    @Column(length = 500, nullable = false)
    private String content;

    //private long recommends;



}
