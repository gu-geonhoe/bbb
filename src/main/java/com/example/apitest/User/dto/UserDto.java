package com.example.apitest.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserDto {
/*
    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String userName;


        @NotBlank
        @Email
        private String email;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private long userId;

        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String userName;


        @NotBlank
        @Email
        private String email;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        private String password;

        public void setUserId(long userId){
            this.userId = userId;
        }
    }


    @AllArgsConstructor
    @Getter
    public static class response {
        private long userId;
        private String userName;
        private String email;
        private String password;

    }*/
}
