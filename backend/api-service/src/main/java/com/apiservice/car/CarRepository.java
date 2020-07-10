package com.apiservice.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByDeletedIsFalse();

    Optional<Car> findByIdAndDeletedIsFalse(long id);

    @Modifying
    @Query("UPDATE Car SET deleted = true where id = :id and deleted = false")
    int deleteById(long id);
}
