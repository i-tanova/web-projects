package com.tanovait.graphql.service;

import com.tanovait.graphql.entity.Dog;
import com.tanovait.graphql.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogServiceImpl implements DogService {
    @Autowired
    private DogRepository dogRepository;

    @Override
    public List<Dog> getAllDogs() {
        return (List<Dog>) dogRepository.findAll();
    }
}
