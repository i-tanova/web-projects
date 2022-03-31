package com.udacity.exercise.chat.controller;

import com.udacity.exercise.chat.model.ChatForm;
import com.udacity.exercise.chat.model.ChatMessage;
import com.udacity.exercise.chat.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public String postMessage(@ModelAttribute("message") ChatForm message, Model model) {
        System.out.println("postMessage" + message);
        chatService.addMessages(message);
        model.addAttribute("messages1", chatService.getMessages());
        message.setMessage("");
        return "chat";
    }

    @GetMapping
    public String getMessages(@ModelAttribute("message") ChatForm message, Model model) {
        System.out.println("getMessages" + chatService.getMessages());
        model.addAttribute("messages1", chatService.getMessages());
        return "chat";
    }

    @ModelAttribute("allMessageTypes")
    public String[] allMessageTypes() {
        return new String[]{"Say", "Shout", "Whisper"};
    }
}
