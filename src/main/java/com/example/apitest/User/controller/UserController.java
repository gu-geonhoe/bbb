package com.example.apitest.User.controller;


import com.example.apitest.User.entity.User;
import com.example.apitest.User.dto.UserPatchDto;
import com.example.apitest.User.dto.UserPostDto;
import com.example.apitest.User.mapper.UserMapper;
import com.example.apitest.User.service.UserService;
import com.example.apitest.response.MultiResponseDto;
import com.example.apitest.response.SingleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/user")  //유저 관련 모든 페이지
@Validated
@Slf4j
public class UserController {
private final UserService userService;
private final UserMapper mapper;

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }


    @PostMapping("/logout")  //로그아웃
    public String logOut(){
        return "로그아웃되었습니다.";
    }
    @PostMapping("/login")  //로그인
    public String login( @RequestParam String userName,
                        @RequestParam String password){
/*
        userService.verifyUserNameAndPassword(userName,password);*/
        return "로그인 성공";

    }

    @PostMapping("/signup")  //회원 가입 , PostUser
    public ResponseEntity signup(@Valid @RequestBody UserPostDto userDto){
        User user =
                userService.createUser(mapper.userPostDtoToUser(userDto));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.userToUserResponseDto(user)),
                HttpStatus.CREATED);
    }

    @GetMapping("/mypage/{user-id}")//회원 정보 조회
    public ResponseEntity getUser(
            @PathVariable("user-id") @Positive long userId){


        User user = userService.findUser(userId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.userToUserResponseDto(user))
                , HttpStatus.OK);
    }

    @GetMapping("/{user-id}")//다른 회원 정보 조회
    public ResponseEntity getOtherUser(
            @PathVariable("user-id") @Positive long userId){

        User user = userService.findUser(userId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.userToUserResponseDto(user))
                , HttpStatus.OK);
    }

    @PatchMapping("/mypage/edit/{user-id}")  //회원 정보 수정
    public ResponseEntity patchUser(
            @PathVariable("user-id") @Positive long userId,
            @Valid @RequestBody UserPatchDto userPatchDto) {

       userPatchDto.setUserId(userId);

       User user =
                userService.updateUser(mapper.userPatchDtoToUser(userPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.userToUserResponseDto(user)),
                HttpStatus.OK);
    }

    @GetMapping //전체 회원 조회
    public ResponseEntity getUsers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<User> pageUsers = userService.findUsers(page - 1, size);
        List<User> users = pageUsers.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.usersToUserResponseDtos(users),
                        pageUsers),
                HttpStatus.OK);
    }


    @DeleteMapping("/delete/{user-id}")  //회원 삭제
    public ResponseEntity userDelete(
            @PathVariable("user-id") long userId){
        String result = userService.deleteUser(userId);

        // map <- key: message , value : 삭제되었습ㄴ디ㅏ
        return new ResponseEntity(result, HttpStatus.NO_CONTENT);
    }
}
