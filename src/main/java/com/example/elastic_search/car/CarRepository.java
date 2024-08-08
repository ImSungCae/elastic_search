package com.example.elastic_search.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByCarType(String carType);
    List<Car> findByCarTypeAndAndDisplacementCCAndRegistrationDateAndUsagePurpose(
            String carType,
            Integer displacementCC,
            String registrationDate,
            String usagePurpose);
}
