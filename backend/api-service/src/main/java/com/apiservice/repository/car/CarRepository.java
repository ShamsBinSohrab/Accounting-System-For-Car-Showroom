package com.apiservice.repository.car;

import com.apiservice.entity.tenant.car.Car;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

  Optional<Car> findByChassisNo(String chassisNo);
}
