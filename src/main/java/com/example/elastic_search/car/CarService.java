package com.example.elastic_search.car;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarDocumentRepository carDocumentRepository;
    private final CustomCarDocumentRepository customCarDocumentRepository;

    public void processCSVFile(String filename){
        List<Car> cars = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader(filename))){
            String[] header = reader.readNext(); // 헤더 스킵
            String[] line;
            while((line = reader.readNext()) != null){
                Car car = Car.builder()
                        .carType(line[0])
                        .carName(line[1])
                        .registrationDate(line[2])
                        .displacementCC(Integer.parseInt(line[3]))
                        .weightKg(Integer.parseInt(line[4]))
                        .vehicleType(line[5])
                        .seatingCapacity(Integer.parseInt(line[6]))
                        .usagePurpose(line[7])
                        .replacementCycle(line[8])
                        .build();
                cars.add(car);
            }

            // DB에 먼저 저장하여 ID를 생성
            carRepository.saveAll(cars);

            // CarDocument 리스트 생성
            List<CarDocument> carDocuments = cars.stream().map(car -> CarDocument.builder()
                    .id(String.valueOf(car.getId())) // 이제 ID가 생성됨
                    .carType(car.getCarType())
                    .carName(car.getCarName())
                    .registrationDate(car.getRegistrationDate())
                    .displacementCC(car.getDisplacementCC())
                    .weightKg(car.getWeightKg())
                    .vehicleType(car.getVehicleType())
                    .seatingCapacity(car.getSeatingCapacity())
                    .usagePurpose(car.getUsagePurpose())
                    .replacementCycle(car.getReplacementCycle())
                    .build()).collect(Collectors.toList());

            // Elasticsearch에 저장
            carDocumentRepository.saveAll(carDocuments);

        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
    }


    public List<Car> searchByJpaCarType(String carType){
        return carRepository.findByCarType(carType);
    }
    public List<CarDocument> searchByEsCarType(String carType ){
//        PageRequest pageRequest = PageRequest.of(page, size);
        List<CarDocument> resultPage = carDocumentRepository.findByCarType(carType);
        return resultPage;
    }

    public List<Car> searchJpa(String carType,
                               Integer displacementCC,
                               String registrationDate,
                               String usagePurpose){
        return carRepository.findByCarTypeAndAndDisplacementCCAndRegistrationDateAndUsagePurpose(
                carType,
                displacementCC,
                registrationDate,
                usagePurpose);
    }
    public List<CarDocument> searchElastic(String carType,
                                           Integer displacementCC,
                                           String registrationDate,
                                           String usagePurpose){
//        PageRequest pageRequest = PageRequest.of(page, size);
        List<CarDocument> resultPage = carDocumentRepository.searchByCarTypeAndDisplacementCCAndRegistrationDateAndUsagePurpose(
                carType,
                displacementCC,
                registrationDate,
                usagePurpose);
        return resultPage;
    }

    public List<CarDocument> searchCustomElastic(String carType,
                                                 Integer displacementCC,
                                                 String registrationDate,
                                                 String usagePurpose){
        List<CarDocument> resultPage = customCarDocumentRepository.findByCarTypeAndAndDisplacementCCAndRegistrationDateAndUsagePurpose(
                carType,
                displacementCC,
                registrationDate,
                usagePurpose);
        return resultPage;
    }

}
