package com.example.apitest.User.entity;




import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.audit.Auditable;

import com.example.apitest.comment.entity.Comment;
import com.example.apitest.tag.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(length = 15, nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    private String roles;
    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }


    //// 회원이 삭제되면 작성한 글도 삭제 : cascade = CascadeType.REMOVE

   //question과 연결 -> USER(1) : QUESTION(N)
    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<Question> questions = new ArrayList<>();
    //mappedBy로 QUESTION 엔티티에 있는 user가 연관 관계의 주인이라고 선언


    //Answer와 연결 -> USER(1) : ANSWER(N)
    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();
    //mappedBy로 ANSWER 엔티티에 있는 user가 연관 관계의 주인이라고 선언

    @OneToMany(mappedBy = "answers", cascade = CascadeType.REMOVE) //REMOVE  PERSIST ALL
    private List<Comment> comments = new ArrayList<>();


    public User(long userId, String userName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void setQuestion(Question question){
        //질문 추가 기능
    }
    /*
    연관 관계 편의 메서드
    public void addTag(Tag tag){
        this.tags.add(tag);
        //무한 루프에 빠지지 않도록 체크
        if(tag.getQuestion() != this)  { // tag의 질문이 this와 다르다면
            tag.setQuestion(this);        // question의 tags에 이 question을 추가
        }
    }*/

/*
    public void setAnswer(Answer answer){
        //답글 추가 기능
    }

    public  void setComment(Comment comment){
        //댓글 추가 기능
    }
*/



}
