package com.example.elastic_search.car;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    // CSV 파일을 처리하는 엔드포인트
    @PostMapping("/upload-csv")
    public void uploadCSV(@RequestParam("filename") String filename) {
        carService.processCSVFile(filename);
    }

    // JPA를 사용하여 자동차 타입으로 검색하는 엔드포인트
    @GetMapping("/search/jpa")
    public List<Car> searchByJpaCarType(
            @RequestParam("carType") String carType,
            @RequestParam("displacementCC") Integer displacementCC,
            @RequestParam("registrationDate") String registrationDate,
            @RequestParam("usagePurpose") String usagePurpose

    ) {
        return carService.searchJpa(carType,displacementCC,registrationDate,usagePurpose);
    }

    // Elasticsearch를 사용하여 자동차 타입으로 검색하는 엔드포인트
    @GetMapping("/search/es")
    public List<CarDocument> searchByEsCarType(
            @RequestParam("carType") String carType,
            @RequestParam("displacementCC") Integer displacementCC,
            @RequestParam("registrationDate") String registrationDate,
            @RequestParam("usagePurpose") String usagePurpose
            )
    {
        return carService.searchElastic(carType,displacementCC,registrationDate,usagePurpose);
    }
    // CustomElasticsearch를 사용하여 자동차 타입으로 검색하는 엔드포인트
    @GetMapping("/search/customEs")
    public List<CarDocument> searchByCustomEsCarType(
            @RequestParam("carType") String carType,
            @RequestParam("displacementCC") Integer displacementCC,
            @RequestParam("registrationDate") String registrationDate,
            @RequestParam("usagePurpose") String usagePurpose
    )
    {
        return carService.searchCustomElastic(carType,displacementCC,registrationDate,usagePurpose);
    }
}
