package com.udacity.bootstrap.service;

import com.udacity.bootstrap.entity.Dog;
import com.udacity.bootstrap.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DogServiceImpl implements DogService {
    @Autowired
    DogRepository dogRepository;

    @Override
    public List<Dog> retrieveDogs() {
        return (List<Dog>) dogRepository.findAll();
    }

    @Override
    public List<String> retrieveDogBreeds() {
        return ((List<Dog>) dogRepository.findAll()).stream().map(this::getDogBreed).collect(Collectors.toList());
    }

    private String getDogBreed(Dog dog) {
        return dog.getBreed();
    }

    @Override
    public String retrieveDogBreedById(Long id) throws ResourceNotFoundException {
        Optional<Dog> dog = dogRepository.findById(id);
        if (dog.isPresent()) {
            return dog.get().getBreed();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public List<String> retrieveDogNames() {
        return ((List<Dog>) dogRepository.findAll()).stream().map(this::getDogName).collect(Collectors.toList());
    }

    private String getDogName(Dog dog) {
        return dog.getName();
    }
}
