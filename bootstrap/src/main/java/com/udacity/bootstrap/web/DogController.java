package com.udacity.bootstrap.web;

import com.udacity.bootstrap.entity.Dog;
import com.udacity.bootstrap.service.DogService;
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

    @GetMapping("/dog/breed/{id}")
    public ResponseEntity<String> getDogBreedById(@PathVariable String id) {
        Long dogId = Long.parseLong(id);
        try {
            String breed = dogService.retrieveDogBreedById(dogId);
            return new ResponseEntity(breed, HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dog/breed")
    public ResponseEntity<List<String>> getDogBreeds() {
        List<String> breeds = dogService.retrieveDogBreeds();
        return new ResponseEntity(breeds, HttpStatus.OK);
    }

    @GetMapping("/dog/name")
    public ResponseEntity<List<String>> getDogNames() {
        List<String> names = dogService.retrieveDogNames();
        return new ResponseEntity(names, HttpStatus.OK);
    }
}
