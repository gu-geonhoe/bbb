package com.example.apitest.Question.mapper;



import com.example.apitest.Question.dto.*;
import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.Question.entitiy.QuestionTag;
import com.example.apitest.User.entity.User;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.tag.entity.Tag;
import com.example.apitest.tag.repository.TagRepository;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {



    //questionpost -> question  작업 완료 후 questionService의 createQuestion 메서드로 이동
    default Question questionPostDtoToQuestion(QuestionPostDto questionPostDto,  User user){
        Question question = new Question();


        question.setUser(user);  // question 객체에 userid에 해당하는 유저 정보 대입
        question.setUserName(user.getUserName());
        question.setQuestionTitle(questionPostDto.getQuestionTitle()); // questionpostdto의 제목 받아오기
        question.setContent(questionPostDto.getContent()); //questionpostdto의 내용 받아오기

        List<QuestionTag> questionTags = questionPostDto.getQuestionTags().stream()
                //questionpostDto에서 입력 받은 태그들을 가공 처리해 QuestionTag 타입의 list에 대입
                //questionPostDto의 QuestionTags는  List<QuestionTagDto> 타입

                .map(questionTagDto -> {
                    //map을 통해 questionTagDto 타입의 요소들을 questionTag 타입으로 변경해준다.
                    QuestionTag questionTag = new QuestionTag();

                    Tag tag = new Tag();
                    //새로운 태그를 만들어 questionTag에 대입할 것

                    //map의 결과로 반환해줄 questionTag 객체 내부 필드 Tag의 값을 세팅해준다.
                    tag.setTagId(questionTagDto.getTagId());
                    /*tag.setTagValue(questionTagDto.getTagValue());
                    tag.setTagInfo(questionTagDto.getTagInfo());*/

                    //questionPostDto를 통해 입력받은 QuestionTagDto의 tagId를 받아서 tag의 tagId로 세팅
                    //tagId -> findtagId를 통해 태그가 존재하는지 확인하고
                    // 입력받은 tagId와 tagValue가 일치하는지 확인하는 작업이 필요하지만 여기서 tagRepository를 사용할 수 없다.
                    // controller 에서 입력받은 tag정보가 유효한지 확인하는 작업을 추가


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


        List<QuestionTag> questionTags = question.getQuestionTags();
        List<Answer> answers = question.getAnswers();

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setUserName(question.getUserName());
        questionResponseDto.setUser(question.getUser());

        questionResponseDto.setQuestionStatus(question.getQuestionStatus());
        questionResponseDto.setQuestionTitle(question.getQuestionTitle());
        questionResponseDto.setContent(question.getContent());
        questionResponseDto.setCreatedAt(question.getCreatedAt());
        questionResponseDto.setModifiedAt(question.getModifiedAt());


        if(!answers.isEmpty()){  //question의 answer가 null이 아니라면
            questionResponseDto.setAnswers(answers); //questionResponseDto의 answers에 question의 answers를 대입
        }
        questionResponseDto.setQuestionTags(questionTagsToQuestionTagResponseDtos(questionTags));

        return questionResponseDto;
    }

    default List<QuestionTagResponseDto> questionTagsToQuestionTagResponseDtos(
            List<QuestionTag> questionTags){
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
