package com.example.test.service;

import com.example.test.model.Country;
import com.example.test.repository.ICountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CountryService implements ICountryService {
    @Autowired
    private ICountryRepository iCountryRepository;
    @Override
    public Iterable<Country> findAll() {
        return iCountryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return iCountryRepository.findById(id);
    }

    @Override
    public Country save(Country country) {
        return iCountryRepository.save(country);
    }

    @Override
    public void remove(Long id) {
        iCountryRepository.deleteById(id);
    }
}
