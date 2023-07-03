package com.example.test.controller;

import com.example.test.model.City;
import com.example.test.model.Country;
import com.example.test.service.ICityService;
import com.example.test.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CityController {

    @Autowired
    private ICityService iCityService;

    @Autowired
    private ICountryService iCountryService;
    @GetMapping("/search/{name}")
    public ResponseEntity<Iterable<City>> searchByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(iCityService.findAllByNameContaining(name), HttpStatus.OK);
    }
    @GetMapping("/country")
    public ResponseEntity<Iterable<Country>> getCountries() {
        return new  ResponseEntity<>(iCountryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<City>> getCities() {
        return new  ResponseEntity<>(iCityService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/api/city")
    public ResponseEntity<City> createCity(@RequestBody City city) {
        return new ResponseEntity<>(iCityService.save(city), HttpStatus.CREATED);
    }
    @DeleteMapping("/api/city/{id}")
    public void deleteCity(@PathVariable Long id) {
        iCityService.remove(id);
    }
    @GetMapping("/api/city/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        Optional<City> cityOptional = iCityService.findById(id);
        if (!cityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cityOptional.get(), HttpStatus.OK);
    }
    @PutMapping("/api/city/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City city) {
        Optional<City> cityOptional = iCityService.findById(id);
        if (!cityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        city.setId(id);
        return new ResponseEntity<>(iCityService.save(city), HttpStatus.OK);
    }

    @ModelAttribute("countries")
    public Iterable<Country> countryIterable(){
        return iCountryService.findAll();
    }


    @GetMapping("/create-city")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/city/create");
        modelAndView.addObject("city", new City());
        return modelAndView;
    }

    @PostMapping("/create-city")
    public ModelAndView saveCity(@ModelAttribute("city") City city) {
        iCityService.save(city);
        ModelAndView modelAndView = new ModelAndView("/city/create");
        modelAndView.addObject("city", new City());
        modelAndView.addObject("message", "New city created successfully");
        return modelAndView;
    }
    @GetMapping("/cities")
    public ModelAndView listCity() {
        ModelAndView modelAndView = new ModelAndView("/city/list");
        modelAndView.addObject("cities", iCityService.findAll());
        return modelAndView;
    }
    @GetMapping("/edit-city/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<City> city = iCityService.findById(id);
        if (city.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/city/edit");
            modelAndView.addObject("city", city.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit-city")
    public ModelAndView updateCity(@ModelAttribute("city") City city) {
        iCityService.save(city);
        ModelAndView modelAndView = new ModelAndView("/city/edit");
        modelAndView.addObject("city", city);
        modelAndView.addObject("message", "city updated successfully");
        return modelAndView;
    }
    @GetMapping("/delete-city/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<City> city = iCityService.findById(id);
        if (city.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/city/delete");
            modelAndView.addObject("city", city.get());
            return modelAndView;

        } else {
            return new ModelAndView("/error.404");
        }
    }
    @PostMapping("/search-city")
    public ModelAndView listCityByName(@ModelAttribute("search") String name) {
        ModelAndView modelAndView = new ModelAndView("/city/list");
        modelAndView.addObject("cities", iCityService.findAllByNameContaining(name));
        return modelAndView;
    }

    @PostMapping("/delete-city")
    public String deleteCity(@ModelAttribute("city") City city) {
        iCityService.remove(city.getId());
        return "redirect:cities";
    }

}
