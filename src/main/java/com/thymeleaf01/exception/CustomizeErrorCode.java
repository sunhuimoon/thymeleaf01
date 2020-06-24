package com.thymeleaf01.exception;


public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你找的问题不存在");

    @Override
    public String getMessage() {
        return message;
    }
    private String message;

    CustomizeErrorCode(String message){
        this.message = message;
    }
}
