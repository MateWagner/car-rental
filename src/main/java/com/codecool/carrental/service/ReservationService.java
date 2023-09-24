package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.controller.dto.ReservationWithCarNameAndId;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.exception.BadRequestException;
import com.codecool.carrental.repository.ReservationRepository;
import com.codecool.carrental.utils.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    protected Set<Long> getNotAvailableCarsIdInPeriod(LocalDate dateFrom, LocalDate dateTo) {
        dateInputValidation(dateFrom, dateTo);
        return reservationRepository.findReservedCarIdInPeriod(dateFrom, dateTo);
    }

    protected Long createNewReservation(ReservationRequest reservationRequest, Car car) {
        try {
            return reservationRepository.save(ReservationMapper.requestToReservation(reservationRequest, car)).getId();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Can't create new car due to data integrity violation");
        }
    }

    public List<ReservationWithCarNameAndId> getAllReservationToAdmin() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::ReservationToAdmin)
                .collect(Collectors.toList());
    }

    private void dateInputValidation(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.equals(dateTo))
            throw new BadRequestException("Can't accept reservation less then one day");
        if (dateFrom.isBefore(LocalDate.now()) || dateTo.isBefore(dateFrom))
            throw new BadRequestException("Start date of reservation can't be older than today and can't be grater then Finish day");
    }
}
