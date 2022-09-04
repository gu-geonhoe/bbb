package com.example.apitest.specification;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.entitiy.QuestionTag;
import com.example.apitest.tag.entity.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class QuestionTagSpecification {
    public static Specification<QuestionTag> equalTag(Tag tag) {
        return new Specification<QuestionTag>() {
            @Override
            public Predicate toPredicate(Root<QuestionTag> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("tag"),tag);
            }
        };

    }

    public static Specification<QuestionTag> equalQuestion(Question question) {
        return new Specification<QuestionTag>() {
            @Override
            public Predicate toPredicate(Root<QuestionTag> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("question"),question);
            }
        };
    }
}
