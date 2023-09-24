package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.entity.Reservation;
import com.codecool.carrental.exception.BadRequestException;
import com.codecool.carrental.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);

    @ParameterizedTest
    @MethodSource("dateArguments")
    public void testDateInputValidationOnGetAvailableCarsInPeriod(LocalDate dateFrom, LocalDate dateTo) {
        assertThrows(BadRequestException.class, () -> reservationService.getNotAvailableCarsIdInPeriod(dateFrom, dateTo));
    }

    @Test
    public void testGetNotAvailableCarsIdInPeriod() {
        when(reservationRepository.findReservedCarIdInPeriod(TODAY, TOMORROW)).thenReturn(Collections.emptySet());
        assertEquals(Collections.emptySet(), reservationService.getNotAvailableCarsIdInPeriod(TODAY, TOMORROW));
        verify(reservationRepository, times(1)).findReservedCarIdInPeriod(TODAY, TOMORROW);
    }

    @Test
    public void testCreateNewReservationSadPath() {
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
        Car car = new Car();
        when(reservationRepository.save(any(Reservation.class))).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(BadRequestException.class, () -> reservationService.createNewReservation(request, car));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testCreateNewReservation() {
        long id = 1;
        ReservationRequest request = new ReservationRequest(
                "",
                "",
                "",
                "",
                TODAY,
                TOMORROW,
                id
        );
        Car car = new Car();
        Reservation reservation = new Reservation();
        reservation.setId(id);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        assertEquals(id, reservationService.createNewReservation(request, car));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testGetAllReservationToAdmin() {
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), reservationService.getAllReservationToAdmin());
        verify(reservationRepository, times(1)).findAll();
    }

    public static Stream<Arguments> dateArguments() {
        return Stream.of(
                Arguments.of(
                        LocalDate.now(),
                        LocalDate.now()
                ),  // Start date equal to end date
                Arguments.of(
                        LocalDate.now().minusDays(1),
                        LocalDate.now().plusDays(1)
                ),  // Start date before today
                Arguments.of(
                        LocalDate.now().plusDays(1),
                        LocalDate.now()
                )  // End date before start date
        );
    }
}
