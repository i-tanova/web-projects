package com.udacity.exercise.chat.service;

import com.udacity.exercise.chat.mapper.MessageMapper;
import com.udacity.exercise.chat.model.ChatForm;
import com.udacity.exercise.chat.model.ChatMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    public ChatService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    private MessageMapper messageMapper;


    public List<ChatMessage> getMessages() {
        return messageMapper.getMessages();
    }

    public void addMessages(ChatForm message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ChatMessage message1 = new ChatMessage();
        message1.setMessageText(message.getMessage());
        message1.setUsername(authentication.getName());
        message1.setMode(message.getMode());
        this.messageMapper.saveMessage(message1);
    }
}
