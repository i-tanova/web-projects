package com.tanovait.graphql.web;

import com.tanovait.graphql.entity.Location;
import com.tanovait.graphql.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location")
    public ResponseEntity<List<Location>> getLocations() {
        List<Location> response = locationService.getAllLocations();
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
