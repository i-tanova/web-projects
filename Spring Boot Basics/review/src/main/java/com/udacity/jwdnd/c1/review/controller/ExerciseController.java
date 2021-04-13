package com.udacity.jwdnd.c1.review.controller;

import com.udacity.jwdnd.c1.review.ExerciseFormModel;
import com.udacity.jwdnd.c1.review.service.ExerciseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExerciseController {

    private ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }


    @GetMapping("/exercise")
    public String getMapp(@ModelAttribute("messageForm") ExerciseFormModel messageForm, Model model){
        model.addAttribute("list", exerciseService.getGreetings());
        return "exercise";
    }

    @PostMapping("/animal")
    public String postMapp(@ModelAttribute("messageForm") ExerciseFormModel messageForm, Model model){
        model.addAttribute("list", exerciseService.getGreetings());
        return "exercise";
    }

}
