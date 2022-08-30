package com.example.apitest.tag.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TagPostDto {


    @NotBlank(message = "공백 불가")
    private String tagValue;

    @NotBlank(message = "공백 불가")
    private String tagInfo;

}
