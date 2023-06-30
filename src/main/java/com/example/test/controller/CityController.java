package com.example.test.controller;


import com.example.test.model.City;
import com.example.test.service.ICityService;
import com.example.test.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("/city")
@CrossOrigin("*")

public class CityController {

    @Autowired
    private ICityService iCityService;

    @Autowired
    private ICountryService iCountryService;

    @GetMapping
    public ResponseEntity<Iterable<City>> getCities() {
        return new  ResponseEntity<>(iCityService.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) {
        return new ResponseEntity<>(iCityService.save(city), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        iCityService.remove(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        Optional<City> cityOptional = iCityService.findById(id);
        if (!cityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cityOptional.get(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City city) {
        Optional<City> cityOptional = iCityService.findById(id);
        if (!cityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        city.setId(id);
        return new ResponseEntity<>(iCityService.save(city), HttpStatus.OK);
    }

}
