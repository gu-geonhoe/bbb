package com.example.apitest;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.entitiy.QuestionTag;
import com.example.apitest.User.entity.User;
import com.example.apitest.tag.entity.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


//JPA Specification 이용하여 쿼리 조건 다루기
public class QuestionSpecification {

    public static Specification<Question> equalUser(User user) {  //user는 1 ,
        return new Specification<Question>() {
            @Override// 자동 추가됨
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("user"),user);
            }
        };
    }

  /*  public static Specification<Question> equalQuestionTags(List<QuestionTag> questionTags) {  //user는 1 ,
        return new Specification<Question>() {
            @Override// 자동 추가됨
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("questionTags"),questionTags);
            }
        };
    }*/



}
