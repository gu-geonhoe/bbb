package com.example.apitest.tag.repository;

import com.example.apitest.Question.entitiy.Question;
import com.example.apitest.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long>, JpaSpecificationExecutor<Question> {

    Optional<Tag> findByTagValue(String tagValue);

}
