package com.codecool.carrental.repository;

import com.codecool.carrental.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r.car.id FROM Reservation r WHERE r.dateFrom BETWEEN :dateFrom AND :dateTo OR r.dateTo BETWEEN :dateFrom AND :dateTo ")
    Set<Long> findReservedCarIdInPeriod(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

}
