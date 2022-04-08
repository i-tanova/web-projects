package com.tanovait.graphql.mutator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.tanovait.graphql.entity.Dog;
import com.tanovait.graphql.exception.BreedNotFoundException;
import com.tanovait.graphql.exception.DogNotFoundException;
import com.tanovait.graphql.repository.DogRepository;

import java.util.List;
import java.util.Optional;

public class MutatorDog implements GraphQLMutationResolver {
    private DogRepository dogRepository;

    public MutatorDog(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Boolean deleteDogBreed(String breed) {
        List<Dog> dogs = dogRepository.findByBreed(breed);
        if (dogs == null) {
            throw new BreedNotFoundException("Not found", breed);
        } else {
            dogRepository.deleteAll(dogs);
        }
        return true;
    }

    public Dog updateDogName(String name, Long id) {
        Optional<Dog> dogOptional = dogRepository.findById(id);
        if (dogOptional.isPresent()) {
            Dog dog = dogOptional.get();
            dog.setName(name);
            dogRepository.save(dog);
            return dog;
        } else {
            throw new DogNotFoundException("Not found", id);
        }
    }

}

