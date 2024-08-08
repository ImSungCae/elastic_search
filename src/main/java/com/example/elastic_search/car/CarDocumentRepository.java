package com.example.elastic_search.car;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CarDocumentRepository extends ElasticsearchRepository<CarDocument, Long> {
    List<CarDocument> findByCarType(String carType);

    List<CarDocument> searchByCarTypeAndDisplacementCCAndRegistrationDateAndUsagePurpose(
            String carType,
            Integer displacementCC,
            String registrationDate,
            String usagePurpose
    );

    List<CarDocument> findByCarTypeAndAndDisplacementCCAndRegistrationDateAndUsagePurpose(
            String carType,
            Integer displacementCC,
            String registrationDate,
            String usagePurpose);
}
