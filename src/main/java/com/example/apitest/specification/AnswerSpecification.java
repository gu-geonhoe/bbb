package com.example.apitest.specification;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.answer.entitiy.Answer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AnswerSpecification {

    public static Specification<Answer> equalUser(User user){
        return new Specification<Answer>() {
            @Override
            public Predicate toPredicate(Root<Answer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("user"),user);
            }
        };
    }

    public static Specification<Answer> equalQuestion(Question question){
        return new Specification<Answer>() {
            @Override
            public Predicate toPredicate(Root<Answer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("question"),question);
            }
        };
    }
}
