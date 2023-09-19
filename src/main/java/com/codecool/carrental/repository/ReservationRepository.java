package com.codecool.carrental.repository;

import com.codecool.carrental.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByDateFromGreaterThanEqualAndDateToLessThanEqual(LocalDate dateFrom, LocalDate dateTo);

    @Query("SELECT r FROM Reservation r WHERE (r.dateFrom BETWEEN :dateFrom AND :dateTo) OR (r.dateTo BETWEEN :dateFrom AND :dateTo) ")
    List<Reservation> findReservationInRange(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
