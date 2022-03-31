package com.udacity.jwdnd.c1.review;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 *
 * Examine component lifecicle
 *
 * Run the application and see the logs of when components are created
 *
 */
@Component
public class MessageService {
    private String message;

    public MessageService(String message){
        this.message = message;
    }

    public String uppercase(){
        return message.toUpperCase(Locale.ROOT);
    }

    public String lowercase(){
        return message.toLowerCase(Locale.ROOT);
    }

    /**
     *
     *   This method will be called after the Component is created
     *   as if you call this in the constructor
     *
     */
    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating MessageService bean");
    }
}
