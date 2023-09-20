package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.repository.ReservationRepository;
import com.codecool.carrental.utils.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    protected Set<Long> getNotAvailableCarsIdInPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return reservationRepository.findReservedCarIdInPeriod(dateFrom, dateTo);

    }

    protected Long createNewReservation(ReservationRequest reservationRequest, Car car) {
        return reservationRepository.save(ReservationMapper.requestToReservation(reservationRequest, car)).getId();
    }
}
