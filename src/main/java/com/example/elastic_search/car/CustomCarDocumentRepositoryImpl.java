package com.example.elastic_search.car;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CustomCarDocumentRepositoryImpl implements CustomCarDocumentRepository{
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<CarDocument> findByCarTypeAndAndDisplacementCCAndRegistrationDateAndUsagePurpose(String carType,
                                                                                                 Integer displacementCC,
                                                                                                 String registrationDate,
                                                                                                 String usagePurpose){

        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .must(m -> m.match(ma -> ma.field("carType").query(carType)))
                                .must(m -> m.match(ma -> ma.field("displacementCC").query(displacementCC)))
                                .must(m -> m.match(ma -> ma.field("registrationDate").query(registrationDate)))
                                .must(m -> m.match(ma -> ma.field("usagePurpose").query(usagePurpose)))
                        )
                )
                .withPageable(PageRequest.of(0,3000))
                .build();
        SearchHits<CarDocument> searchHits = elasticsearchOperations.search(query, CarDocument.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
