package com.tanovait.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.tanovait.graphql.repository.LocationRepository;
import com.tanovait.graphql.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {
    private LocationRepository locationRepository;

    public Query(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Iterable<Location> findAllLocations() {
        return locationRepository.findAll();
    }
}

