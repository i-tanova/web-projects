package com.udacity.jwdnd.c1.review.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {

    private List<String> greetings = new ArrayList<>();

    public List<String> getGreetings() {
        return greetings;
    }

    public void setGreeting(String greetings) {
        this.greetings.add(greetings);
    }
}
