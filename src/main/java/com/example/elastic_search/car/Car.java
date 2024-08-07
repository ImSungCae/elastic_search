package com.example.elastic_search.car;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carType;
    private String carName;
    private String registrationDate;
    private int displacementCC;
    private int weightKg;
    private String vehicleType;
    private int seatingCapacity;
    private String usagePurpose;
    private String replacementCycle;
}
