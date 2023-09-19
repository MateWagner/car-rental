package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.entity.Reservation;
import com.codecool.carrental.repository.ReservationRepository;
import com.codecool.carrental.utils.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    protected List<Long> getNotAvailableCarsId(LocalDate dateFrom, LocalDate dateTo) {
        List<Reservation> x = reservationRepository
                .findReservationInRange(dateFrom, dateTo);
        System.out.println(x);
        return x.stream()
                .map(reservation -> reservation.getCar().getId())
                .collect(Collectors.toList());
    }

    protected Long createNewReservation(ReservationRequest reservationRequest, Car car) {
        return reservationRepository.save(ReservationMapper.requestToReservation(reservationRequest, car)).getId();
    }
}
