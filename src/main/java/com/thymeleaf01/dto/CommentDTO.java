package com.thymeleaf01.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
//{
//        "parentId" :"1",
//        "content" : "急急急急急急较简洁",
//        "type": "1"
//        }
