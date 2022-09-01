package com.example.apitest.User.mapper;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.User.dto.UserDto;
import com.example.apitest.User.entity.User;
import com.example.apitest.User.dto.UserPatchDto;
import com.example.apitest.User.dto.UserPostDto;
import com.example.apitest.User.dto.UserResponseDto;
import com.example.apitest.answer.entitiy.Answer;
import com.example.apitest.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User userPostDtoToUser(UserPostDto userPostDto);
    User userPatchDtoToUser(UserPatchDto userPatchDto);
    UserResponseDto userToUserResponseDto(User user);

    List<UserResponseDto> usersToUserResponseDtos(List<User> users);


}
