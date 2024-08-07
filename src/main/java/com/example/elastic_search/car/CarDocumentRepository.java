package com.example.elastic_search.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDocumentRepository extends ElasticsearchRepository<CarDocument, Long> {
    Page<CarDocument> searchByCarType(String carType, Pageable pageable);
}
