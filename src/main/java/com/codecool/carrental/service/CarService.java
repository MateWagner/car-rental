package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.exception.NotFoundException;
import com.codecool.carrental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final ReservationService reservationService;

    public List<Car> getAvailableCarsInPeriod(LocalDate dateFrom, LocalDate dateTo) {
        List<Long> notAvailableCarsId = reservationService.getNotAvailableCarsId(dateFrom, dateTo);

        if (notAvailableCarsId.isEmpty())
            return carRepository.findAll();
        return carRepository.findAllByIdNotIn(notAvailableCarsId);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Long reserveCar(ReservationRequest reservationRequest) {
//        if (!isCarAvailable(reservationRequest)) {
//            throw new BadRequestException("Not available Car with id " + reservationRequest.getCarId());
//        } TODO
        Car actualCar = getCarById(reservationRequest.getCarId());
        return reservationService.createNewReservation(reservationRequest, actualCar);
    }

    public Car getCarById(Long id) {
        return carRepository.findCarById(id)
                .orElseThrow(() -> new NotFoundException("Can't find Car with id: " + id));
    }

    private boolean isCarAvailable(ReservationRequest reservationRequest) {
        List<Long> notAvailableCarsId = reservationService.getNotAvailableCarsId(reservationRequest.getDateFrom(), reservationRequest.getDateTo());
        return !notAvailableCarsId.contains(reservationRequest.getCarId());
    }

    public List<Car> getAllCar() {
        return carRepository.findAll();
    }
}
