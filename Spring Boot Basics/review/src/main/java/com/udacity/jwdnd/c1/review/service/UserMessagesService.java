package com.udacity.jwdnd.c1.review.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserMessagesService {

    private List<String> userMessages;

    /**
     * This is bad practice. Use method @PostConstruct instead of constructor
     */
//    public UserMessagesService() {
//        this.userMessages = new ArrayList<>();
//    }

    @PostConstruct
    public void postConstruct(){
        this.userMessages = new ArrayList<>();
    }

    public void addMessage(String message){
        userMessages.add(message);
    }

    public List<String> getUserMessages(){
        return new ArrayList<>(this.userMessages);
    }

}
