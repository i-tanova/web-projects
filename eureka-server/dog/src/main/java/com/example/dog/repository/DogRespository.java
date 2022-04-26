package com.example.dog.repository;

import com.example.dog.entity.Dog;
import org.springframework.data.repository.CrudRepository;

public interface DogRespository extends CrudRepository<Dog, Long> {
}
