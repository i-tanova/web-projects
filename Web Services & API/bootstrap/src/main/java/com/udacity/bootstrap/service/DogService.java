package com.udacity.bootstrap.service;

import com.udacity.bootstrap.entity.Dog;
import com.udacity.bootstrap.entity.Location;

import java.util.List;

public interface DogService {

    List<Dog> retrieveDogs();

    List<String> retrieveDogBreeds();

    String retrieveDogBreedById(Long id) throws ResourceNotFoundException;

    List<String> retrieveDogNames();
}

