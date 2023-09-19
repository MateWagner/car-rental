package com.codecool.carrental.repository;

import com.codecool.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByIdNotIn(List<Long> reservedCarsId);
}
