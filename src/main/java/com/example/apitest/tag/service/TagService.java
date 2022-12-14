package com.example.apitest.tag.service;



import com.example.apitest.exception.BusinessLogicException;
import com.example.apitest.exception.ExceptionCode;
import com.example.apitest.tag.entity.Tag;
import com.example.apitest.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public Tag createTag(Tag tag){
        Tag savedTag = tag;
        return tagRepository.save(savedTag);
    }

    public Tag updateTag(Tag tag) {
        Tag findTag = findVerifiedTag(tag.getTagId());

        Optional.ofNullable(tag.getTagValue())
                .ifPresent(tagValue -> findTag.setTagValue(tagValue));
        Optional.ofNullable(tag.getTagInfo())
                .ifPresent(tagInfo -> findTag.setTagInfo(tagInfo));

        return tagRepository.save(findTag);
    }

    public Tag findTag(long tagId){
        return findVerifiedTag(tagId);
    }

    public Page<Tag> findTags(int page, int size) {
        return tagRepository.findAll(PageRequest.of(page, size,
                Sort.by("tagId").descending()));
    }


    public void deleteTag(long tagId) {
        Tag findTag = findVerifiedTag(tagId);
        tagRepository.delete(findTag);
    }


    public Tag findVerifiedTag(long tagId) {
        Optional<Tag> optionalTag =
                tagRepository.findById(tagId);
        Tag findTag =
                optionalTag.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.TAG_ID_NOT_FOUND));

        return findTag;
    }

    public Tag findVerifiedTagIdAndTagValue(long tagId, String tagValue) {
        Optional<Tag> optionalTag =
                tagRepository.findById(tagId);
        Tag findTag =
                optionalTag.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.TAG_ID_NOT_FOUND));
        if(!tagValue.equals(findTag.getTagValue())){
            throw new BusinessLogicException(ExceptionCode.TAG_VALUE_NOT_FOUND);
        }
        return findTag;
    }
/*
    public Tag findTagByTagValue(String tagValue) {
        Optional<Tag> optionalTag =
                tagRepository.findByTagValue(tagValue);
        Tag findTag =
                optionalTag.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.TAG_VALUE_NOT_FOUND));
        return findTag;
    }
*/
    /*public Page<Question> searchTagQuestionList(Tag tag, int page, int size) {

        Specification<Question> spec = Specification.where(QuestionSpecification.equalTag(tag));
        return tagRepository.findAll(spec, PageRequest.of(page,size,
                Sort.by("createdAt").descending()));
        //Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);

    }*/



}
