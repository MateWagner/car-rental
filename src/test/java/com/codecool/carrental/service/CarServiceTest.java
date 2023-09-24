package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.NewCarDTO;
import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.exception.BadRequestException;
import com.codecool.carrental.exception.NotFoundException;
import com.codecool.carrental.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private ReservationService reservationService;
    @InjectMocks
    private CarService carService;
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);

    @Test
    public void allCarAvailableInPeriod() {
        Set<Long> emptyCarIdSet = Collections.emptySet();
        when(reservationService.getNotAvailableCarsIdInPeriod(TODAY, TOMORROW)).thenReturn(emptyCarIdSet);
        carService.getAvailableCarsInPeriod(TODAY, TOMORROW);
        verify(carRepository, times(1)).findCarByIsActiveTrue();
    }

    @Test
    public void notAllCarAvailableInPeriod() {
        long carId = 1;
        Set<Long> carIdSet = Set.of(carId);
        when(reservationService.getNotAvailableCarsIdInPeriod(TODAY, TOMORROW)).thenReturn(carIdSet);
        carService.getAvailableCarsInPeriod(TODAY, TOMORROW);
        verify(carRepository, times(1)).findAllByIdNotInAndIsActive(carIdSet, true);
    }

    @Test
    public void testReserveCarAndCarAlreadyTaken() {
        long carId = 1;
        ReservationRequest request = new ReservationRequest(
                "",
                "",
                "",
                "",
                TODAY,
                TOMORROW,
                carId
        );
        Set<Long> notAvailableCarId = Set.of(carId);
        when(reservationService.getNotAvailableCarsIdInPeriod(TODAY, TOMORROW)).thenReturn(notAvailableCarId);
        assertThrows(BadRequestException.class, () -> carService.reserveCar(request));
        verify(reservationService, times(1)).getNotAvailableCarsIdInPeriod(TODAY, TOMORROW);
    }

    @Test
    public void testReserveCarAndCarInactiveOrNotExistsWithEmptySet() {
        long carId = 1;
        ReservationRequest request = new ReservationRequest(
                "",
                "",
                "",
                "",
                TODAY,
                TOMORROW,
                carId
        );
        Set<Long> emptyCarId = Collections.emptySet();
        when(reservationService.getNotAvailableCarsIdInPeriod(TODAY, TOMORROW)).thenReturn(emptyCarId);
        when(carRepository.findCarByIdAndIsActive(carId, true)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> carService.reserveCar(request));
        verify(reservationService, times(1)).getNotAvailableCarsIdInPeriod(TODAY, TOMORROW);
        verify(carRepository, times(1)).findCarByIdAndIsActive(carId, true);
    }

    @Test
    public void testReserveCarAndCanReserveNotEmptyReservedCarIdSet() {
        long carId = 1;
        long carId2 = 2;
        ReservationRequest request = new ReservationRequest(
                "",
                "",
                "",
                "",
                TODAY,
                TOMORROW,
                carId
        );
        Set<Long> notAvailableCarId = Set.of(carId2);
        Car car = new Car();
        when(reservationService.getNotAvailableCarsIdInPeriod(TODAY, TOMORROW)).thenReturn(notAvailableCarId);
        when(carRepository.findCarByIdAndIsActive(carId, true)).thenReturn(Optional.of(car));
        when(reservationService.createNewReservation(request, car)).thenReturn(carId);
        assertEquals(carId, carService.reserveCar(request));
        verify(reservationService, times(1)).getNotAvailableCarsIdInPeriod(TODAY, TOMORROW);
        verify(carRepository, times(1)).findCarByIdAndIsActive(carId, true);
        verify(reservationService, times(1)).createNewReservation(request, car);
    }

    @Test
    public void testReserveCarAndCanReserveEmptyReservedCarIdSet() {
        long carId = 1;
        long carId2 = 2;
        ReservationRequest request = new ReservationRequest(
                "",
                "",
                "",
                "",
                TODAY,
                TOMORROW,
                carId
        );
        Set<Long> emptyCarId = Collections.emptySet();
        Car car = new Car();
        when(reservationService.getNotAvailableCarsIdInPeriod(TODAY, TOMORROW)).thenReturn(emptyCarId);
        when(carRepository.findCarByIdAndIsActive(carId, true)).thenReturn(Optional.of(car));
        when(reservationService.createNewReservation(request, car)).thenReturn(carId);
        assertEquals(carId, carService.reserveCar(request));
        verify(reservationService, times(1)).getNotAvailableCarsIdInPeriod(TODAY, TOMORROW);
        verify(carRepository, times(1)).findCarByIdAndIsActive(carId, true);
        verify(reservationService, times(1)).createNewReservation(request, car);
    }

    @Test
    public void testGetCarByIdAndNotFound() {
        long carId = 1;
        when(carRepository.findCarById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> carService.getCarById(carId));
        verify(carRepository, times(1)).findCarById(any(Long.class));
    }

    @Test
    public void testGetCarByIdAndFound() {
        long carId = 1;
        Car car = new Car();
        when(carRepository.findCarById(any(Long.class))).thenReturn(Optional.of(car));
        assertEquals(car, carService.getCarById(carId));
        verify(carRepository, times(1)).findCarById(any(Long.class));
    }

    @Test
    public void testGetAllCarToAdminCallTeFindAll() {
        when(carRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), carService.getAllCarToAdmin());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateCarSadPath() {
        Car car = new Car();
        when(carRepository.save(any(Car.class))).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(BadRequestException.class, () -> carService.updateCar(car));
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    public void testAddNewCarSadPath() {
        NewCarDTO carDTO = new NewCarDTO();
        when(carRepository.save(any(Car.class))).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(BadRequestException.class, () -> carService.addNewCar(carDTO));
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    public void testAddNewCarAndReturnId() {
        long carId = 1;
        NewCarDTO carDTO = new NewCarDTO();
        Car car = new Car();
        car.setId(carId);
        when(carRepository.save(any(Car.class))).thenReturn(car);
        assertEquals(carId, carService.addNewCar(carDTO));
        verify(carRepository, times(1)).save(any(Car.class));
    }
}

