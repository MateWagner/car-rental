package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.NewCarDTO;
import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.exception.BadRequestException;
import com.codecool.carrental.exception.NotFoundException;
import com.codecool.carrental.repository.CarRepository;
import com.codecool.carrental.utils.CarMapper;
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
        dateInputValidation(dateFrom, dateTo);
        Set<Long> notAvailableCarsId = reservationService.getNotAvailableCarsIdInPeriod(dateFrom, dateTo);
        if (notAvailableCarsId.isEmpty())
            return carRepository.findCarByIsActiveTrue();
        return carRepository.findAllByIdNotInAndIsActive(notAvailableCarsId, true);
    }

    @Transactional()
    public Long reserveCar(ReservationRequest reservationRequest) {
        dateInputValidation(reservationRequest.getDateFrom(), reservationRequest.getDateTo());

        if (!isCarAvailableInPeriod(reservationRequest)) {
            throw new BadRequestException("Not available Car with id " + reservationRequest.getCarId());
        }

        Car actualCar = getCarByIdAndIsActive(reservationRequest.getCarId(), true);

        return reservationService.createNewReservation(reservationRequest, actualCar);
    }

    private Car getCarByIdAndIsActive(Long id, boolean isActive) {
        return carRepository.findCarByIdAndIsActive(id, isActive)
                .orElseThrow(() -> new NotFoundException("Can't find available Car with id: " + id));
    }

    public Car getCarById(Long id) {
        return carRepository.findCarById(id)
                .orElseThrow(() -> new NotFoundException("Can't find Car with id: " + id));
    }

    private boolean isCarAvailableInPeriod(ReservationRequest reservationRequest) {
        Set<Long> notAvailableCarsId = reservationService.getNotAvailableCarsIdInPeriod(
                reservationRequest.getDateFrom(),
                reservationRequest.getDateTo()
        );
        return !notAvailableCarsId.contains(reservationRequest.getCarId());
    }


    private void dateInputValidation(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.equals(dateTo))
            throw new BadRequestException("Can't accept reservation less then one day");
        if (dateFrom.isBefore(LocalDate.now()) || dateTo.isBefore(dateFrom))
            throw new BadRequestException("Start date of reservation can't be older than today and can't be grater then Finish day");
    }

    public List<Car> getAllCarToAdmin() {
        return carRepository.findAll();
    }

    public void updateCar(Car car) {
        carRepository.save(car);
    }

    public Long addNewCar(NewCarDTO carDTO) {
        return carRepository.save(CarMapper.NewcarDTOToCar(carDTO)).getId();
    }
}
