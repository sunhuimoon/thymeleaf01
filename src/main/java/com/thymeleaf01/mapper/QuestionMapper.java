package com.thymeleaf01.mapper;

import com.thymeleaf01.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_creat,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreat},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
    @Select("select * from question")
    List<Question> list();
}
