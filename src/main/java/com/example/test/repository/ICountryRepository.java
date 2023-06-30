package com.example.test.repository;

import com.example.test.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ICountryRepository extends JpaRepository<Country,Long> {
}
