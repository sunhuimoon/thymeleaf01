package com.thymeleaf01.model;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreat;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}
