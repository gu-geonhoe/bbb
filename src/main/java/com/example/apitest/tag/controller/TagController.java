package com.example.apitest.tag.controller;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.service.QuestionService;
import com.example.apitest.response.MultiResponseDto;
import com.example.apitest.response.SingleResponseDto;
import com.example.apitest.tag.dto.TagPatchDto;
import com.example.apitest.tag.dto.TagPostDto;
import com.example.apitest.tag.entity.Tag;
import com.example.apitest.tag.mapper.TagMapper;
import com.example.apitest.tag.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


// uses unchecked or unsafe operations.
//Recompile with -Xlint:unchecked for details. 나오니 확인 필요

@RestController
@RequestMapping("/tag")  // tag목록 출력하기 위한 페이지
@Validated
@Slf4j
public class TagController {



    private final TagService tagService;
    private final TagMapper mapper;
    private final QuestionService questionService;

    private final int size = 10;

    public TagController(TagService tagService, TagMapper mapper, QuestionService questionService){
        this.tagService = tagService;
        this.mapper = mapper;
        this.questionService = questionService;
    }

    @PostMapping("/register") // 태그 등록
    public ResponseEntity registerTag(@Valid @RequestBody TagPostDto tagPostDto){
        Tag tag =
                tagService.createTag(mapper.tagPostDtoToTag(tagPostDto));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.tagToTagResponseDto(tag)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{tag-id}") // 태그 정보 조회
    public ResponseEntity getTagInfo(@PathVariable("tag-id") @Positive long tagId){

        Tag tag = tagService.findTag(tagId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.tagToTagResponseDto(tag)),
                HttpStatus.OK);
    }

    @PatchMapping("/edit/{tag-id}") // 태그 정보 수정
    public ResponseEntity editTag(@PathVariable("tag-id") @Positive long tagId,
                                  @Valid @RequestBody TagPatchDto tagPatchDto) {

        tagPatchDto.setTagId(tagId);
        Tag tag =
                tagService.updateTag(mapper.tagPatchDtoToTag(tagPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.tagToTagResponseDto(tag)),
                HttpStatus.OK);
    }

    @GetMapping //전체 태그 조회
    public ResponseEntity getTags(@Positive @RequestParam int page) {

        Page<Tag> pageTags = tagService.findTags(page-1,size);
        List<Tag> tags = pageTags.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.tagsToTagResponseDtos(tags), pageTags),
                HttpStatus.OK);
    }


    @DeleteMapping("/delete/{tag-id}")  //태그 삭제
    public ResponseEntity userDelete(
            @PathVariable("tag-id") long tagId){
        tagService.deleteTag(tagId);
        return new ResponseEntity("태그 삭제 완료", HttpStatus.NO_CONTENT);
    }

}
