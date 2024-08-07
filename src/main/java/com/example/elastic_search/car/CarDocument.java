package com.example.elastic_search.car;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Builder
@Document(indexName = "cars")
public class CarDocument {
    @Id
    private String id;
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
