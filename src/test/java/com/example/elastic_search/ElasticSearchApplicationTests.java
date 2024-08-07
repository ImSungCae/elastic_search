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
        for(Car s : results){
//            System.out.println(s.getCarName());
        }
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
        int size = 10000;

        List<CarDocument> results = carService.searchByEsCarType(carType, page, size);
        // 시간 측정 끝
        System.out.println("size : " + results.size());
        for(CarDocument s : results){
//            System.out.println(s.getCarName());
        }
        System.out.println();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Elasticsearch 검색 성능 측정: " + elapsedTime + "ms");
    }

//    @Autowired
//    private TaskRepository taskRepository;
//    @Autowired
//    private TaskSearchRepository taskSearchRepository;
//
//    @Test
//    @DisplayName("RDBMS 데이터 삽입 성능 테스트")
//    public void RDMS_데이터_삽입_테스트(){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//
//        LocalDateTime startTime = LocalDateTime.now();
//        String formattedStartTime = startTime.format(formatter);
//        log.info("현재 시간 : {}", formattedStartTime);
//        for (int i = 0; i < 10000; i++){
//            String title = "title" + i;
//            String description = "description" + i;
//            LocalDateTime createdAt = LocalDateTime.now();
//            taskRepository.save(Task.builder()
//                    .title(title)
//                    .description(description)
//                    .createdAt(createdAt)
//                    .build());
//        }
//
//        LocalDateTime endTime = LocalDateTime.now();
//        String formattedEndTime = endTime.format(formatter);
//        log.info("조회 후 시간 : {}", formattedEndTime);
//
//        Duration duration = Duration.between(startTime,endTime);
//        log.info("시간 차이 : " + duration.toMillis() + "ms");
//    }
//
//    @Test
//    @DisplayName("ES 데이터 삽입 성능 테스트")
//    public void ES_데이터_삽입_테스트(){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//
//        LocalDateTime startTime = LocalDateTime.now();
//        String formattedStartTime = startTime.format(formatter);
//        log.info("현재 시간 : {}", formattedStartTime);
//        for (int i = 0; i < 232419; i++){
//            String title = "제목" + i;
//            String description = "설명" + i;
//            LocalDateTime createdAt = LocalDateTime.now();
//            taskSearchRepository.save(TaskDocument.builder()
//                    .id((long) i + 1)
//                    .title(title)
//                    .description(description)
//                    .createdAt(createdAt)
//                    .build());
//        }
//
//        LocalDateTime endTime = LocalDateTime.now();
//        String formattedEndTime = endTime.format(formatter);
//        log.info("조회 후 시간 : {}", formattedEndTime);
//
//        Duration duration = Duration.between(startTime,endTime);
//        log.info("시간 차이 : " + duration.toMillis() + "ms");
//    }

}
