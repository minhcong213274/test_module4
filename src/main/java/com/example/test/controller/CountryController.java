package com.example.test.controller;

import com.example.test.model.City;
import com.example.test.model.Country;
import com.example.test.service.ICityService;
import com.example.test.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
@RestController

public class CountryController {
    @Autowired
    private ICityService iCityService;

    @Autowired
    private ICountryService iCountryService;
    @GetMapping("/countries")
    public ModelAndView listCountry() {
        Iterable<Country> countries = iCountryService.findAll();
        ModelAndView modelAndView = new ModelAndView("/country/list");
        modelAndView.addObject("countries", countries);
        return modelAndView;
    }

    @GetMapping("/create-country")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/country/create");
        modelAndView.addObject("country", new Country());
        return modelAndView;
    }

    @PostMapping("/create-country")
    public ModelAndView saveCountry(@ModelAttribute("country") Country country) {
        iCountryService.save(country);

        ModelAndView modelAndView = new ModelAndView("/country/create");
        modelAndView.addObject("country", new Country());
        modelAndView.addObject("message", "New country created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-country/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Country> country = iCountryService.findById(id);
        if (country.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/country/edit");
            modelAndView.addObject("country", country.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-country")
    public ModelAndView updateCountry(@ModelAttribute("country") Country country) {
        iCountryService.save(country);
        ModelAndView modelAndView = new ModelAndView("/country/edit");
        modelAndView.addObject("country", country);
        modelAndView.addObject("message", "country updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-country/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Country> country = iCountryService.findById(id);
        if (country.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/country/delete");
            modelAndView.addObject("country", country.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-country")
    public String deleteCountry(@ModelAttribute("country") Country country) {
        iCountryService.remove(country.getId());
        return "redirect:countries";
    }

    @GetMapping("/view-country/{id}")
    public ModelAndView viewCountry(@PathVariable("id") Long id){
        Optional<Country> countryOptional = iCountryService.findById(id);
        if(!countryOptional.isPresent()){
            return new ModelAndView("/error.404");
        }

        Iterable<City> cities = iCityService.findAllByCountry(countryOptional.get());

        ModelAndView modelAndView = new ModelAndView("/country/view");
        modelAndView.addObject("country", countryOptional.get());
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }
}
