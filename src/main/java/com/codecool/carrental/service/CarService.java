package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.exception.BadRequestException;
import com.codecool.carrental.exception.NotFoundException;
import com.codecool.carrental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final ReservationService reservationService;

    public List<Car> getAvailableCarsInPeriod(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.equals(dateTo))
            throw new BadRequestException("Can't accept reservation less then one day");

        Set<Long> notAvailableCarsId = reservationService.getNotAvailableCarsIdInPeriod(dateFrom, dateTo);
        if (notAvailableCarsId.isEmpty())
            return carRepository.findAll();
        return carRepository.findAllByIdNotIn(notAvailableCarsId);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Long reserveCar(ReservationRequest reservationRequest) {
        if (!isCarAvailable(reservationRequest)) {
            throw new BadRequestException("Not available Car with id " + reservationRequest.getCarId());
        }
        Car actualCar = getCarById(reservationRequest.getCarId());
        return reservationService.createNewReservation(reservationRequest, actualCar);
    }

    public Car getCarById(Long id) {
        return carRepository.findCarById(id)
                .orElseThrow(() -> new NotFoundException("Can't find Car with id: " + id));
    }

    private boolean isCarAvailable(ReservationRequest reservationRequest) {
        Set<Long> notAvailableCarsId = reservationService.getNotAvailableCarsIdInPeriod(
                reservationRequest.getDateFrom(),
                reservationRequest.getDateTo()
        );
        return !notAvailableCarsId.contains(reservationRequest.getCarId());
    }

    public List<Car> getAllCar() {
        return carRepository.findAll();
    }
}
