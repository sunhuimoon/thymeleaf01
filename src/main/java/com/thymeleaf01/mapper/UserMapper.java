package com.thymeleaf01.mapper;

import com.thymeleaf01.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    //新增用户
    @Insert("INSERT INTO user (name,account_id,token,gmt_create,gmt_modified,avatar_url,bio) VALUES(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{bio})")
    void insert(User user);
    //根据token查用户
    @Select("select * from user where token =#{token}")
    User findByToken(@Param("token") String token);
    //根据id查用户
    @Select("select * from user where id =#{id}")
    User findById(@Param("id")Integer id);
    //根据account_id查用户
    @Select("select * from user where account_id =#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);
    //更改用户信息
    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl},bio = #{bio}")
    void update(User dbUser);
}
