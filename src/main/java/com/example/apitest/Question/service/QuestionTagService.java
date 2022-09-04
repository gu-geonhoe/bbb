package com.example.apitest.Question.service;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.entitiy.QuestionTag;
import com.example.apitest.Question.repository.QuestionTagRepository;
import com.example.apitest.User.entity.User;
import com.example.apitest.exception.BusinessLogicException;
import com.example.apitest.exception.ExceptionCode;
import com.example.apitest.specification.QuestionSpecification;
import com.example.apitest.specification.QuestionTagSpecification;
import com.example.apitest.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class QuestionTagService {
    private final QuestionTagRepository questionTagRepository;

    public QuestionTagService(QuestionTagRepository questionTagRepository){
        this.questionTagRepository = questionTagRepository;
    }

/*    public QuestionTag updateQuestionTag(QuestionTag questionTag) {
        QuestionTag findQuestionTag = findVerifiedQuestionTag(questionTag.getQuestionTagId());

        Optional.ofNullable(findQuestionTag.getTag())
                .ifPresent(tag-> findQuestionTag.setTag(tag));
        Optional.ofNullable(findQuestionTag.getQuestion())
                .ifPresent(question-> findQuestionTag.setQuestion(question));

        return questionTagRepository.save(findQuestionTag);
    }

    public QuestionTag findVerifiedQuestionTag(long questionTagId) {
        Optional<QuestionTag> optionalQuestionTag =
                questionTagRepository.findById(questionTagId);
        QuestionTag findQuestionTag =
                optionalQuestionTag.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.QUESTION_TAG_NOT_FOUND));

        return findQuestionTag;
    }*/

    public void resetQuestionTagByQuestion(Question question){
        Specification<QuestionTag> spec = Specification.where(QuestionTagSpecification.equalQuestion(question));
        List<QuestionTag> originalQuestionTagList = questionTagRepository.findAll(spec);
        questionTagRepository.deleteAll(originalQuestionTagList);
        /*QuestionTag findQuestionTag =
                optionalQuestionTag.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.QUESTION_TAG_NOT_FOUND));
        return findQuestionTag;*/
    }
//equalQuestion
/*    public QuestionTag searchUserQuestionList(User user, int page, int size) {

    Specification<QuestionTag> spec = Specification.where(QuestionTagSpecification.equalQuestion(question));
    return questionRepository.findAll(spec, PageRequest.of(page,size,
            Sort.by("createdAt").descending()));
    //Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);*/

}

