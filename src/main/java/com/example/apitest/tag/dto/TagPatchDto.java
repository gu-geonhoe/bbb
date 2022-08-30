package com.example.apitest.tag.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TagPatchDto {

    private long tagId;

    @NotBlank(message = "공백 불가")
    private String tagValue;

    @NotBlank(message = "공백 불가")
    private String tagInfo;

    public void setTagId(long tagId){ this.tagId = tagId;}
}
