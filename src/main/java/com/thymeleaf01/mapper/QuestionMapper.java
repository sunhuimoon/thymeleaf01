package com.thymeleaf01.mapper;

import com.thymeleaf01.dto.QuestionDTO;
import com.thymeleaf01.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    //新增问题
    @Insert("insert into question (title,description,gmt_creat,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreat},#{gmtModified},#{creator},#{tag})")
    int create(Question question);
    //查询所有问题
    @Select("select * from question ORDER BY gmt_modified  DESC limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);
    //查询问题总条数
    @Select("select count(1) from question")
    Integer count();
    //查询该用户所有问题
    @Select("select * from question where creator = #{userId} ORDER BY gmt_modified DESC limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId,@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);
   //查询该用户问题总条数
    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);
    //根据问题id查问题
    @Select("select * from question where id = #{id}")
    QuestionDTO getById(@Param("id") Integer id);

    @Update("update question set title = #{title}, description =#{description}, gmt_modified=#{gmtModified},tag=#{tag} where id =#{id}")
    int update(Question question);

    @Update("update question set view_count = view_count+1 where id =#{id}")
    void incView(Integer id);

}
