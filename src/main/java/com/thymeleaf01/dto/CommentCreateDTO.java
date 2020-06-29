package com.thymeleaf01.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
//创建评论的接口json
//{
//        "parentId" :"1",
//        "content" : "急急急急急急较简洁",
//        "type": "1"
//        }
