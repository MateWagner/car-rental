package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.RequestedPeriod;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.repository.CarRepository;
import com.codecool.carrental.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public List<Car> getAvailableCarsInPeriod(RequestedPeriod requestedPeriod) {
        List<Long> notAvailableCarsId = reservationRepository
                .findAllByDateFromGreaterThanEqualAndDateToLessThanEqual(
                        requestedPeriod.getDateFrom(),
                        requestedPeriod.getDateTo()
                )
                .stream()
                .map(reservation -> reservation.getCar().getId())
                .collect(Collectors.toList());

        List<Car> availableCars = carRepository.findAllByIdNotIn(notAvailableCarsId);
        return availableCars;
    }

}
