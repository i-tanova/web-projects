package com.udacity.bootstrap.controller;

import com.udacity.bootstrap.entity.Dog;
import com.udacity.bootstrap.service.DogService;
import com.udacity.bootstrap.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DogController {

    private DogService dogService;

    @Autowired
    public void setDogService(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping("/dog")
    public ResponseEntity<List<Dog>> getAllDogs() {
        List<Dog> list = dogService.retrieveDogs();
        return new ResponseEntity<List<Dog>>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/breed")
    public ResponseEntity<List<String>> retrieveDogBreeds() {
        List<String> list = dogService.retrieveDogBreeds();
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/breed/{id}")
    public ResponseEntity<String> retrieveDogBreedById(@PathVariable Long id) {
        try {
           String breed = dogService.retrieveDogBreedById(id);
           return new ResponseEntity<String>(breed, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/name")
    public ResponseEntity<List<String>> retrieveDogNames() {
        List<String> list = dogService.retrieveDogNames();
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }
}


