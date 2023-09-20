package com.codecool.carrental.repository;

import com.codecool.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByIdNotInAndIsActive(Set<Long> reservedCarsId, boolean isActive);

    List<Car> findCarByIsActiveTrue();

    Optional<Car> findCarById(Long id);

    Optional<Car> findCarByIdAndIsActive(Long id, boolean isActive);
}
