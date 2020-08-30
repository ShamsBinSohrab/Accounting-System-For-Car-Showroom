package com.apiservice.service.sell;

import com.apiservice.entity.tenant.sell.CarSellRecord;
import com.apiservice.entity.tenant.sell.SellRecord;
import com.apiservice.repository.car.CarRepository;
import com.apiservice.repository.sell.CarSellRecordRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarSellRecordService {

    private final CarSellRecordRepository carSellRecordRepository;
    private final CarRepository carRepository;

    @Transactional(readOnly = true)
    public List<CarSellRecord> getAll() {
        return carSellRecordRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CarSellRecord getById(long id) {
        return carSellRecordRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.of(SellRecord.class, id));
    }

    @Transactional
    public void save(CarSellRecord carSellRecord) {
        carSellRecordRepository.save(carSellRecord);
    }

    @Transactional
    public void delete(long id) {
        carSellRecordRepository.deleteById(id);
    }
}
