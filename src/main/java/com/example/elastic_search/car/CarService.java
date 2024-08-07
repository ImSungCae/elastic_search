package com.example.elastic_search.car;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarDocumentRepository carDocumentRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    public void processCSVFile(String filename){

        try{
            CSVReader reader = new CSVReader(new FileReader(filename));
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

                carRepository.save(car); // DB 저장

                CarDocument carDocument = CarDocument.builder()
                        .id(String.valueOf(car.getId())) // Ensure this matches
                        .carType(car.getCarType())
                        .carName(car.getCarName())
                        .registrationDate(car.getRegistrationDate())
                        .displacementCC(car.getDisplacementCC())
                        .weightKg(car.getWeightKg())
                        .vehicleType(car.getVehicleType())
                        .seatingCapacity(car.getSeatingCapacity())
                        .usagePurpose(car.getUsagePurpose())
                        .replacementCycle(car.getReplacementCycle())
                        .build();

                carDocumentRepository.save(carDocument);

            }
        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
    }

    public List<Car> searchByJpaCarType(String carType){
        return carRepository.findByCarType(carType);
    }
    public List<CarDocument> searchByEsCarType(String carType, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CarDocument> resultPage = carDocumentRepository.searchByCarType(carType,pageRequest);
        return resultPage.getContent();
    }

}
