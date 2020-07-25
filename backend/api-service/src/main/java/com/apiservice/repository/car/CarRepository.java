package com.apiservice.repository.car;

import com.apiservice.entity.car.Car;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

  List<Car> findAllByDeletedIsFalse();

  Optional<Car> findByIdAndDeletedIsFalse(long id);

  Optional<Car> findByChassisNo(String chassisNo);

  @Modifying
  @Query("UPDATE Car SET deleted = true where id = :id and deleted = false")
  int deleteById(long id);
}
