package com.example.apitest.tag.mapper;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.tag.dto.TagPatchDto;
import com.example.apitest.tag.dto.TagPostDto;
import com.example.apitest.tag.dto.TagResponseDto;
import com.example.apitest.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    default Tag tagPostDtoToTag(TagPostDto tagPostDto){
        Tag tag = new Tag();
        tag.setTagValue(tagPostDto.getTagValue());
        tag.setTagInfo(tagPostDto.getTagInfo());
        return tag;
    }
    default Tag tagPatchDtoToTag(TagPatchDto tagPatchDto){
        Tag tag = new Tag();
        tag.setTagId(tagPatchDto.getTagId());
        tag.setTagValue(tagPatchDto.getTagValue());
        tag.setTagInfo(tagPatchDto.getTagInfo());
        return tag;
    }
    TagResponseDto tagToTagResponseDto(Tag tag);/*{
        TagResponseDto tagResponseDto = new TagResponseDto();
        tagResponseDto.setTagId(tag.getTagId());
        tagResponseDto.setTagValue(tag.getTagValue());
        tagResponseDto.setTagInfo(tag.getTagInfo());

        return tagResponseDto;
    }*/

    List<TagResponseDto> tagsToTagResponseDtos(List<Tag> tags);


}
