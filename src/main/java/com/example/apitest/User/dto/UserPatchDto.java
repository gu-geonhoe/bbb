package com.example.apitest.User.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserPatchDto {

    private long userId;

    @NotBlank(message = "공백 불가")
    private String userName;


    @NotBlank(message = "공백 불가")
    @Email
    private String email;


    @NotBlank(message = "공백 불가")
    private String password;

    public void setUserId(long userId) { this.userId = userId; }
}
