package com.example.apitest.exception;

import lombok.Getter;

public enum ExceptionCode {
    USER_NOT_FOUND(404, "Member not found"),
    USER_EXISTS(409, "Member Already exists"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    ANSWER_ID_EXISTS(409, "Answer Id Already exists"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    TAG_ID_NOT_FOUND(404, "Tag ID not found"),
    TAG_VALUE_NOT_FOUND(404, "Tag Value not found"),
    COMMENT_NOT_FOUND(404,"Comment not found"),

    QUESTION_TAG_NOT_FOUND(404,"Question Tag not found"),
    NO_ACCESS(404," You don't have access to the requested object");

  /*  CANNOT_CHANGE_ORDER(403, "Order can not change"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_MEMBER_STATUS(400, "Invalid member status");

   */



    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
