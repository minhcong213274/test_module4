package com.example.test.controller;

import com.example.test.model.City;
import com.example.test.model.Country;
import com.example.test.service.ICityService;
import com.example.test.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("/country")
@CrossOrigin("*")
public class CountryController {
    @Autowired
    private ICountryService iCountryService;
    @Autowired
    private ICityService iCityService;
    @GetMapping
    public ResponseEntity<Iterable<City>> getCities() {
        return new  ResponseEntity<>(iCityService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<Iterable<Country>> allCountry() {
        return new ResponseEntity<>(iCountryService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        return new ResponseEntity<>(iCountryService.save(country), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        iCountryService.remove(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        Optional<Country> countryOptional = iCountryService.findById(id);
        if (!countryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(countryOptional.get(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country country) {
        Optional<Country> countryOptional = iCountryService.findById(id);
        if (!countryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        country.setId(id);
        return new ResponseEntity<>(iCountryService.save(country), HttpStatus.OK);
    }


    @ModelAttribute("city")
    public Iterable<City> cities(){
        return iCityService.findAll();
    }
}
