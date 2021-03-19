package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileAlreadyExitsException;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileController {

    private FileService fileService;
    private UserMapper userMapper;
    private NoteService noteService;
    private CredentialsService credentialsService;

    public FileController(FileService noteService, UserMapper userMapper, NoteService noteService1, CredentialsService credentialsService) {
        this.fileService = noteService;
        this.userMapper = userMapper;
        this.noteService = noteService1;
        this.credentialsService = credentialsService;
    }

    @PostMapping("/files")
    public String uploadFile(Model model, @RequestParam("fileUpload") MultipartFile file, Authentication authentication,
                             @ModelAttribute("note") NoteForm note, @ModelAttribute("credential") CredentialForm credentialForm) {
        System.out.println("postFile" + file);
        String username = authentication.getName();
        int userId = userMapper.getUser(username).getUserId();
        if(file.isEmpty()){
            model.addAttribute("errorMessage", "File is empty");
        }else {
            File fileObj = new File();
            fileObj.setContenttype(file.getContentType());
            fileObj.setFilename(file.getOriginalFilename());
            fileObj.setUserid(userId);
            fileObj.setFilesize(file.getSize() + "");

            try {
                fileObj.setFiledata(file.getBytes());
                fileService.createFile(fileObj);
                model.addAttribute("success", "File saved!");
            } catch (FileAlreadyExitsException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "File already exists!");
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Unknown error!");
            }
        }
        model.addAttribute("tab", "nav-files-tab");
        model.addAttribute("files", fileService.getUserFiles(userId));
        model.addAttribute("notes", noteService.getUserNotes(userId));
        model.addAttribute("credentials", credentialsService.getUserCredentials(userId));

        return "home";
    }

    @RequestMapping(value = {"/files/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewFile(@PathVariable(name = "id") String id,
                                           HttpServletResponse response, HttpServletRequest request) {
        File file = fileService.getFileById(Integer.parseInt(id));
        byte[] fileContents = file.getFiledata();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(file.getContenttype()));
        String fileName = file.getFilename();
        httpHeaders.setContentDispositionFormData(fileName, fileName);
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> serverResponse = new ResponseEntity<byte[]>(fileContents, httpHeaders, HttpStatus.OK);
        return serverResponse;
    }

    @RequestMapping(value = "files/delete/{id}")
    private String deleteFile(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes) {
        System.out.println("deleteFile" + id);
        fileService.deleteFile(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("tab", "nav-files-tab");
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/home";
    }
}
