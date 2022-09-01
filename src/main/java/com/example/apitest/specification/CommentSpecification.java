package com.example.apitest.specification;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.comment.entity.Comment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CommentSpecification {
    public static Specification<Comment> equalUser(User user){
        return new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("user"),user);
            }
        };
    }

    public static Specification<Comment> equalAnswer(Answer answer){
        return new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("answer"),answer);
            }
        };
    }
}
