package com.udacity.bootstrap.service;

import com.udacity.bootstrap.entity.Dog;
import com.udacity.bootstrap.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogServiceImpl implements DogService {
    @Autowired
    DogRepository dogRepository;

    @Override
    public List<Dog> retrieveDogs() {
        return (List<Dog>) dogRepository.findAll();
    }

    @Override
    public String retrieveDogBreedById(Long id) throws IllegalAccessException {
        return dogRepository.findById(id).orElseThrow(IllegalAccessException::new).getBreed();
    }

    @Override
    public List<String> retrieveDogBreeds() {
        List<String> breeds = new ArrayList<String>();
        dogRepository.findAll().forEach(dog ->
                breeds.add(dog.getBreed())
        );
        return breeds;
    }

    @Override
    public List<String> retrieveDogNames() {
        List<String> names = new ArrayList<String>();
        dogRepository.findAll().forEach(dog ->
                names.add(dog.getName())
        );
        return names;
    }
}
