package com.example.elastic_search.car;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Builder
@Document(indexName = "cars")
@Setting(settingPath = "static/elastic-settings.json")
@Mapping(mappingPath = "static/elastic-mappings.json")
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
