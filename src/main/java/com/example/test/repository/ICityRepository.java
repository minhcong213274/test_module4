package com.example.test.repository;

import com.example.test.model.City;
import com.example.test.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
  Iterable<City> findAllByCountry(Country country);
  Iterable<City> findAllByNameContaining(String name);

}
