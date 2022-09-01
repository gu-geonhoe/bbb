package com.example.apitest.answer.mapper;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.entity.User;
import com.example.apitest.answer.dto.AnswerPatchDto;
import com.example.apitest.answer.dto.AnswerPostDto;
import com.example.apitest.answer.dto.AnswerResponseDto;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.comment.entity.Comment;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface AnswerMapper {


    default Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto, User user, Question question){
     // controller에서 userId와 questionId로 답변과 매핑되는 USER와 QUESTION을 지정해 받아온다.

        Answer answer = new Answer();
        answer.setUser(user); // answer 객체에 userId에 해당하는 유저 정보 대입
        answer.setUserName(user.getUserName());
        answer.setQuestion(question);// answer 객체에 questionId에 해당하는 질문 정보 대입

        answer.setContent(answerPostDto.getContent());

        return answer;
    }

    default Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto, User user, Question question){
        Answer answer = new Answer();
        answer.setUser(user);

        answer.setAnswerId(answerPatchDto.getAnswerId());
        answer.setUserName(user.getUserName());
        answer.setQuestion(question);// answer 객체에 questionId에 해당하는 질문 정보 대입

        answer.setContent(answerPatchDto.getContent());

        return answer;
    }

    List<AnswerResponseDto> answersToAnswerResponseDtos(List<Answer> answers);

    default AnswerResponseDto answerToAnswerResponseDto(Answer answer){

        List<Comment> comments = answer.getComments();

        AnswerResponseDto answerResponseDto = new AnswerResponseDto();
        //a u q
        answerResponseDto.setAnswerId(answer.getAnswerId());
        answerResponseDto.setUserName(answer.getUserName());
        answerResponseDto.setUser(answer.getUser());

        answerResponseDto.setQuestion(answer.getQuestion());
        answerResponseDto.setContent(answer.getContent());
        answerResponseDto.setCreatedAt(answer.getCreatedAt());
        answerResponseDto.setModifiedAt(answer.getModifiedAt());

        if(!comments.isEmpty()){ //answer의 comment가 null이 아니라면
            answerResponseDto.setComments(comments);
        }

        return answerResponseDto;

    }
}
