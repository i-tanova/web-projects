package com.udacity.exercise.chat.mapper;

import com.udacity.exercise.chat.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO users (username, password, salt, lastname, firstname) VALUES (#{username}, #{password}, #{salt}, " +
            "#{lastName}, #{firstName})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int saveUser(User user);

    @Delete("DELETE FROM users WHERE id = #{userid}")
    void deleteUser(int id);
}