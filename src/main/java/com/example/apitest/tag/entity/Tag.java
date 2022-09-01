package com.example.apitest.tag.entity;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.entitiy.QuestionTag;
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
@Table(name = "TAGS")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @Column(length = 100, nullable = false, unique = true)
    private String tagValue;

    @Column(length = 800, nullable = false)
    private String tagInfo;

   @OneToMany(mappedBy = "tag")
   private List<QuestionTag> questionTags = new ArrayList<>();


  // 연관관계 편의 메서드
  public void addQuestionTag(QuestionTag questionTag){
      this.questionTags.add(questionTag);
      if(questionTag.getTag() != this) {
          questionTag.addTag(this);
      }
  }


/*
    public Tag(long tagId, String tagValue, String tagInfo){
        this.tagId = tagId;
        this.tagValue = tagValue;
        this.tagInfo = tagInfo;
    }
*/

    public String getTagValue() {
        return tagValue;
    }

    public String getTagInfo() {
        return tagInfo;
    }


}
