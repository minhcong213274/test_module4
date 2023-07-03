package com.example.test.service;

import com.example.test.model.City;
import com.example.test.model.Country;

public interface ICityService extends IGeneralService<City>{
    Iterable<City> findAllByCountry(Country country);
    Iterable<City> findAllByNameContaining(String name);


}
