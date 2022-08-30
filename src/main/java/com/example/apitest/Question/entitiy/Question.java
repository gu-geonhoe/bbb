package com.example.apitest.Question.entitiy;


import com.example.apitest.User.entity.User;
import com.example.apitest.audit.Auditable;
import com.example.apitest.tag.entity.Tag;
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

    //다대일 관계에서 N인 question 테이블
    // 다대일 관계에서 1인 User 테이블의 외래키 userid를 갖는다.
    // 다대일 단방향일 때는 다쪽에  @ManyToOne 만 추가
    //// 외래키인 question_id를 지정한다.


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    //@JsonIgnore // 무한 반복 피하기 위해 @JsonIgnore 어노테이션을 추가하여 직렬화에서 제외시키는 방법
    private User user;
    /*    // 다대일에서는 다 쪽이 연관관계 주인이 되므로 @OneToMany에서 mappedBy 속성의 값으로 연관관계 주인을 지정해줘야 합니다
    // 다대일 양방향 관계에서 Tags가 연관관계의 주인이 된다.
    *//*
    객체를 양방향으로 설정하면 외래 키를 관리하는 곳이 2곳이 생깁니다.
    한쪽에서만 관리하도록 하기 위해서 연관관계 주인을 설정할 필요가 있습니다.
    엔티티 매니저는 연관관계 주인 (ex. Tag.question)을 통해서만 외래 키를 관리한다
    - 여기서만 연관관계를 설정할 수 있다.
    - new Tag().setQuestion(new Question())
    - 엔티티 매니저는 연관관계 주인 (ex. Tag.question)를 통해서만 외래 키를 관리한다
    - DB에 반영이 된다
     *//*
    //Caused by: java.lang.IllegalStateException: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : com.example.apitest.tag.entity.Tag.question -> com.example.apitest.Question.entitiy.Question
    // -> 영속성때문에 나는 오류다. FK로 쓰는 객체가 아직 저장이 안 되서 오류가 난다
    // -> join하는쪽에 cascade를 설정해준다. // cascade = CascadeType.ALL
    //출처: https://conservative-vector.tistory.com/entry/오류 [에움길:티스토리]

  // 연관관계 편의 메서드
  public void setQuestion(Question question){
      this.question = question;
      //무한 루프에 빠지지 않도록 체크
      if(!question.getTags().contains(this)) { // question의 tags에서 이 question을 가지고 있지 않다면
          question.getTags().add(this);        // question의 tags에 이 question을 추가
      }*/
    private String userName;

    public void setUser(User user) {
        this.user = user;
    }

    //Tag와 연결
    @OneToMany(mappedBy = "question",cascade = CascadeType.PERSIST)
    private List<QuestionTag> questionTags = new ArrayList<>();

    /*
    cascade = CascadeType.PERSIST ???
     */

    public void addQuestionTag(QuestionTag questionTag) {
        this.questionTags.add(questionTag);
        if(questionTag.getQuestion() != this){
            questionTag.addQuestion(this);
        }
    }



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
