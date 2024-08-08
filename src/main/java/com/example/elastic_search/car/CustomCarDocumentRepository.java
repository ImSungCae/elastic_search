package com.example.elastic_search.car;

import java.util.List;

public interface CustomCarDocumentRepository {
    List<CarDocument> findByCarTypeAndAndDisplacementCCAndRegistrationDateAndUsagePurpose(String carType,
                                                                                          Integer displacementCC,
                                                                                          String registrationDate,
                                                                                          String usagePurpose);
}
