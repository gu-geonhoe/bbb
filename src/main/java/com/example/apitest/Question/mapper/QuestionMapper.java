package com.example.apitest.Question.mapper;



import com.example.apitest.Question.dto.QuestionPatchDto;
import com.example.apitest.Question.dto.QuestionPostDto;
import com.example.apitest.Question.dto.QuestionResponseDto;
import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {



  //  QuestionResponseDto questionToQuestionResponseDto(Question question, List<TotalEntity> totalEntities);

    //전체 질문 조회
    List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions);



    //questionpost -> question
    default Question questionPostDtoToQuestion(QuestionPostDto questionPostDto, User user){
        Question question = new Question();
        question.setUser(user);  // question 객체에 userid에 해당하는 유저 정보 대입
        question.setQuestionTitle(questionPostDto.getQuestionTitle()); // questionpostdto의 제목 받아오기
        question.setContent(questionPostDto.getContent()); //questionpostdto의 내용 받아오기
        return question;
    }

    default Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto, User user){
        Question question = new Question();
        question.setUser(user);
        question.setQuestionId(questionPatchDto.getQuestionId());
        question.setQuestionTitle(questionPatchDto.getQuestionTitle()); // questionPatchDto 제목 받아오기
        question.setContent(questionPatchDto.getContent()); //questionPatchDto 내용 받아오기
        return question;
    }


    //question -> questionResponse
    default QuestionResponseDto questionToQuestionResponseDto(Question question){

       QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setUser(question.getUser());
        questionResponseDto.setQuestionTitle(question.getQuestionTitle());
        questionResponseDto.setContent(question.getContent());
        questionResponseDto.setQuestionStatus(question.getQuestionStatus());
        questionResponseDto.setCreatedAt(question.getCreatedAt());
        questionResponseDto.setModifiedAt(question.getModifiedAt());

        return questionResponseDto;
    }


}
