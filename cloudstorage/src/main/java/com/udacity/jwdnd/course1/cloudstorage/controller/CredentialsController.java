package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialsController {

    private FileService fileService;
    private UserMapper userMapper;
    private NoteService noteService;
    private CredentialsService credentialsService;


    public CredentialsController(FileService fileService, UserMapper userMapper, NoteService noteService,
                                 CredentialsService credentialsService) {
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.noteService = noteService;
        this.credentialsService = credentialsService;
    }

    @PostMapping("/credentials")
    public String addCredential(@ModelAttribute("credential") CredentialForm credentialForm, @ModelAttribute("note") NoteForm note, Model model, Authentication authentication) {
        System.out.println("postCredential" + credentialForm);
        String username = authentication.getName();
        int userId = userMapper.getUser(username).getUserId();
        Credential credential = new Credential();
        credential.setUserid(userId);
        credential.setUsername(credentialForm.getUsername());
        credential.setUrl(credentialForm.getUrl());
        credential.setUnencodedPassword(credentialForm.getPassword());

        if (credentialForm.getId() != null && !credentialForm.getId().isBlank()) {
            credential.setCredentialid(Integer.parseInt(credentialForm.getId()));
            credentialsService.updateCredential(credential);
        } else {
            credentialsService.createCredential(credential);
        }
        model.addAttribute("success", true);
        model.addAttribute("tab", "nav-credentials-tab");
        model.addAttribute("credentials", credentialsService.getUserCredentials(userId));
        model.addAttribute("notes", noteService.getUserNotes(userId));
        model.addAttribute("files", fileService.getUserFiles(userId));
        return "home";
    }


    @RequestMapping(value = "credentials/delete/{id}")
    private String deleteCredential(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes){
        System.out.println("deleteCredential: " + id);
        credentialsService.deleteCredential(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("tab", "nav-credentials-tab");
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/home";
    }


}
