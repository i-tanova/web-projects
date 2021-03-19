package com.udacity.jwdnd.c1.review.controller;

import com.udacity.jwdnd.c1.review.MessageForm;
import com.udacity.jwdnd.c1.review.service.UserMessagesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ThymeleafSampleController {

    UserMessagesService service;

    @RequestMapping("/list")
    public String listTemplate(Model model) {
        ArrayList<String> list = new ArrayList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");
        model.addAttribute("list", list);
        return "list";
    }

    public ThymeleafSampleController(UserMessagesService service) {
        this.service = service;
    }

    @RequestMapping("/unless")
    public String unlessTemplate(Model model) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        model.addAttribute("list", list);
        return "unless";
    }

    /**
     * This method will be called at Get requests
     */
    @GetMapping("/simpleform")
    public String receiveGetReqest(Model model) {
        model.addAttribute("firstVisit", true);
        return "simpleform";
    }


    /**
     * This method will be called at POST request
     */
    @PostMapping("/simpleform")
    public String receiveFormDataPOST(Model model) {
        model.addAttribute("firstVisit", false);
        return "simpleform";
    }

    /**
     * Use service to get data
     */
    @GetMapping("/form")
    public String getList(@ModelAttribute("newMessage") MessageForm newMessage, Model model) {
        model.addAttribute("list", service.getUserMessages());
        return "form";
    }

    /**
     * Use service to put data
     */
    @PostMapping("/form")
    public String setMessageToList(@ModelAttribute("newMessage") MessageForm newMessage, Model model) {
        service.addMessage(newMessage.getText());
        model.addAttribute("list", service.getUserMessages());
        newMessage.setText("");
        return "form";
    }

//    /**
//     *  This is POST request. So add the user object
//     *
//     */
//    @PostMapping("/form")
//    public String receiveUserInput(Model model) {
////        model.ge
////        model.addAttribute("firstVisit", false);
//        return "form";
//    }
}
