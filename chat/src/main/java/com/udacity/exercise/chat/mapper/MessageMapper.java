package com.udacity.exercise.chat.mapper;

import com.udacity.exercise.chat.model.ChatMessage;
import com.udacity.exercise.chat.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {

    @Select("SELECT * FROM messages WHERE id = #{messageid}")
    ChatMessage getMessage(int messageid);

    @Select("SELECT * FROM messages")
    List<ChatMessage> getMessages();

    @Insert("INSERT INTO messages (username, messagetext, mode) VALUES (#{username}, #{messageText}, #{mode})")
    @Options(useGeneratedKeys = true, keyProperty = "messageid")
    void saveMessage(ChatMessage message);

    @Delete("DELETE FROM messages WHERE id = #{messageid}")
    void deleteMessage(int id);

}
