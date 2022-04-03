package com.tanovait.graphql.service;

import com.tanovait.graphql.entity.Location;
import com.tanovait.graphql.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationRepository locationRepository;


    @Override
    public List<Location> getAllLocations() {
        return (List<Location>) locationRepository.findAll();
    }
}
