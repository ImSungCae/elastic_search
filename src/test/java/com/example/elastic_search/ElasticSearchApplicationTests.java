package com.example.elastic_search;

import com.example.elastic_search.car.Car;
import com.example.elastic_search.car.CarDocument;
import com.example.elastic_search.car.CarService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ElasticSearchApplicationTests {

    @Autowired
    private CarService carService;

    @Test
    @DisplayName("csv 저장 테스트")
    public void csv_저장_테스트(){
        try{
            for (int i=0;i<1000;i++){
                carService.processCSVFile("C:\\Users\\임성채\\Downloads\\한국전력기술(주)_업무용 차량 보유 현황_20231231.csv");
            }
        }catch (Exception e){
            log.error("Test failed",e);
            throw e;
        }
    }


    @Test
    void testSearchJpaByCarType(){
        String carType="소형화물";
        long startTime = System.currentTimeMillis();
        List<Car> results = carService.searchByJpaCarType(carType);
        // 시간 측정 끝
        System.out.println("size : " + results.size());
//        for(Car s : results){
//            System.out.println(s.getCarName());
//        }
        // 시간 측정 끝
        System.out.println();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("JPA 검색 성능 측정: " + elapsedTime + "ms");
    }

    @Test
    void testSearchEsByCarName(){
        String carType="소형화물";
        long startTime = System.currentTimeMillis();
        int page = 0;
        int size = 2002;

//        List<CarDocument> results = carService.searchByEsCarType(carType, page, size);
                List<CarDocument> results = carService.searchByEsCarType(carType);
        // 시간 측정 끝
        System.out.println("size : " + results.size());
//        for(CarDocument s : results){
//            System.out.println(s.getCarName());
//        }
        System.out.println();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Elasticsearch 검색 성능 측정: " + elapsedTime + "ms");
    }


}
