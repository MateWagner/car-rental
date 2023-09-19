package com.codecool.carrental.repository;

import com.codecool.carrental.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByDateFromGreaterThanEqualAndDateToLessThanEqual(LocalDate dateFrom, LocalDate dateTo);
}
