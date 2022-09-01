package com.example.apitest.Question.mapper;



import com.example.apitest.Question.dto.QuestionPatchDto;
import com.example.apitest.Question.dto.QuestionPostDto;
import com.example.apitest.Question.dto.QuestionResponseDto;
import com.example.apitest.Question.dto.QuestionTagResponseDto;
import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.entitiy.QuestionTag;
import com.example.apitest.User.entity.User;
import com.example.apitest.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {



    //questionpost -> question
    default Question questionPostDtoToQuestion(QuestionPostDto questionPostDto,  User user){
        Question question = new Question();


        question.setUser(user);  // question 객체에 userid에 해당하는 유저 정보 대입
        question.setUserName(user.getUserName());
        question.setQuestionTitle(questionPostDto.getQuestionTitle()); // questionpostdto의 제목 받아오기
        question.setContent(questionPostDto.getContent()); //questionpostdto의 내용 받아오기
       /* //question 객체에 선택된 태그들 모두 담기
        //question.setTags(tags);

        for(Tag tag : tags)
            question.addTag(tag);
        // create 에 해당하므로 questionId는 자동 생성된다.*/
        List<QuestionTag> questionTags = questionPostDto.getQuestionTags().stream()
                .map(questionTagDto -> {
                    QuestionTag questionTag = new QuestionTag();
                    Tag tag = new Tag();
                    tag.setTagId(questionTagDto.getTagId());
                    tag.setTagValue(questionTagDto.getTagValue());
                    questionTag.addQuestion(question);
                    questionTag.addTag(tag);

                    return questionTag;
                }).collect(Collectors.toList());
        question.setQuestionTags(questionTags);

        return question;
    }


    default Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto, User user){
        Question question = new Question();
        //questionPost에서 받은 tagValue 리스트의 값들을 읽어 해당하는 태그 정보 가져오기
        question.setQuestionId(questionPatchDto.getQuestionId());    //question Id 매칭시켜주기 추가
        question.setUser(user);  // question 객체에 userid에 해당하는 유저 정보 대입
        question.setUserName(user.getUserName());
        question.setQuestionTitle(questionPatchDto.getQuestionTitle()); // questionpostdto의 제목 받아오기
        question.setContent(questionPatchDto.getContent()); //questionpostdto의 내용 받아오기
       /* //question 객체에 선택된 태그들 모두 담기
        //question.setTags(tags);

        for(Tag tag : tags)
            question.addTag(tag);
        // create 에 해당하므로 questionId는 자동 생성된다.*/
        List<QuestionTag> questionTags = questionPatchDto.getQuestionTags().stream()
                .map(questionTagDto -> {
                    QuestionTag questionTag = new QuestionTag();
                    Tag tag = new Tag();
                    tag.setTagId(questionTagDto.getTagId());
                    questionTag.addQuestion(question);
                    questionTag.addTag(tag);

                    return questionTag;
                }).collect(Collectors.toList());
        question.setQuestionTags(questionTags);

        return question;
    }
    List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions);


  /*  default Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto, List<Tag> tags, User user){

        Question question = new Question();

        question.setUser(user);

        for(Tag tag : tags)
            question.addTag(tag);

        question.setQuestionId(questionPatchDto.getQuestionId());
        question.setQuestionTitle(questionPatchDto.getQuestionTitle()); // questionPatchDto 제목 받아오기
        question.setContent(questionPatchDto.getContent()); //questionPatchDto 내용 받아오기
        return question;
    }*/


    //question -> questionResponse
    default QuestionResponseDto questionToQuestionResponseDto(Question question){

        /*QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setQuestionId(question.getQuestionId());

        questionResponseDto.setUser(question.getUser());

        questionResponseDto.setTags(question.getTags());

        questionResponseDto.setQuestionTitle(question.getQuestionTitle());
        questionResponseDto.setContent(question.getContent());
        questionResponseDto.setQuestionStatus(question.getQuestionStatus());
        questionResponseDto.setCreatedAt(question.getCreatedAt());
        questionResponseDto.setModifiedAt(question.getModifiedAt());*/

        List<QuestionTag> questionTags = question.getQuestionTags();

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setUserName(question.getUserName());
        questionResponseDto.setUser(question.getUser());
        questionResponseDto.setQuestionStatus(question.getQuestionStatus());
        questionResponseDto.setQuestionTitle(question.getQuestionTitle());
        questionResponseDto.setContent(question.getContent());
        questionResponseDto.setCreatedAt(question.getCreatedAt());
        questionResponseDto.setModifiedAt(question.getModifiedAt());
        questionResponseDto.setQuestionTags(
                questionTagsToQuestionTagResponseDtos(questionTags)
        );
        return questionResponseDto;
    }
    default List<QuestionTagResponseDto> questionTagsToQuestionTagResponseDtos(
            List<QuestionTag> questionTags) {
        return questionTags
                .stream()
                .map(questionTag -> QuestionTagResponseDto
                        .builder()
                        .tagId(questionTag.getTag().getTagId())
                        .tagValue(questionTag.getTag().getTagValue())
                        .tagInfo(questionTag.getTag().getTagInfo())
                        .questionId(questionTag.getQuestion().getQuestionId())
                        .questionTitle(questionTag.getQuestion().getQuestionTitle())
                        .userName(questionTag.getQuestion().getUser().getUserName())
                        .build())
                .collect(Collectors.toList());
    }


}
