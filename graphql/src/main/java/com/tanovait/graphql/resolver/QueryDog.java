package com.tanovait.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.tanovait.graphql.entity.Dog;
import com.tanovait.graphql.exception.DogNotFoundException;
import com.tanovait.graphql.repository.DogRepository;

import java.util.Optional;

public class QueryDog implements GraphQLQueryResolver {
    private DogRepository dogRepository;

    public QueryDog(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Iterable<Dog> findAllDogs() {
        return dogRepository.findAll();
    }

    public Dog findDogById(Long id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if (optionalDog.isPresent()) {
            return optionalDog.get();
        } else {
            throw new DogNotFoundException("Dog not found", id);
        }
    }

    public Iterable<String> findDogBreeds() {
        return dogRepository.findAllBreed();
    }

    public Iterable<String> findAllDogNames() {
        return dogRepository.findAllName();
    }

    public String findDogBreedById(Long id) {
        String optionalBreed = dogRepository.findBreedById(id);
        if (optionalBreed != null) {
            return optionalBreed;
        } else {
            throw new DogNotFoundException("Dog not found", id);
        }
    }
}
