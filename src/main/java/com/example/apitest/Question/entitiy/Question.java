package com.example.apitest.Question.entitiy;


import com.example.apitest.User.entity.User;
import com.example.apitest.audit.Auditable;
import lombok.AllArgsConstructor;
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
public class Question extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_REQUEST;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    //@OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
   /* private List<TotalEntity> totalEntities = new ArrayList<>();*/

    public void setUser(User user) {
        this.user = user;
    }

    // 답글 추가
    /*public void addAnswer(TotalEntity totalEntity) {
        this.totalEntities.add(totalEntity);
      if (totalEntity.getQuestion() != this) {
            totalEntity.addQuestion(this);
        }
    }*/

    //private long userId;
    //private long questionTagId;

   /* @Column
    private long views;

    @Column
    private long recommends;*/

    @Column(length = 20, nullable = false)
    private String questionTitle;

    @Column(length = 500, nullable = false)
    private String content;

 /*   @Column
    private char deletable;*/

    /*public Question(long questionId,String questionTitle, String content){
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.content = content;
    }*/

    public enum QuestionStatus {
        QUESTION_REQUEST(1, "질문 등록"),
        /*QUESTION_CANCEL(2, "질문 취소"),*/
        QUESTION_COMPLETE(3, "답변 채택 완료");

        @Getter
        private int stepNumber;

        @Getter
        private String stepDescription;

        QuestionStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }
}
