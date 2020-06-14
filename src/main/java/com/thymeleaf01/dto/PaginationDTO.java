package com.thymeleaf01.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    //判断是否有向前
    private boolean showPrevious;
    private boolean showFirstPage;
    //向后按钮
    private boolean showNext;
    //最后一页
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;


    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage= totalPage;
        this.page = page;
        pages.add(page);
        for(int i = 1; i <= 3; i++){
            if(page - i > 0){
                pages.add(0,page -i);
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }

        //是否展示跳转上一页标识
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //是否展示跳转下一页标识
        if(page == totalPage){
            showNext =false;
        }else {
            showNext = true;
        }
        //是否展示到直接到第一页标识
        //contains(1),包含1
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //是否展示到直接到最后一页标识
        if(pages.contains(totalPage)){
            showEndPage =false;
        }else {
            showEndPage = true;
        }
    }
}
