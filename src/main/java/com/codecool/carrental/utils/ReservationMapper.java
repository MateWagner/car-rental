package com.codecool.carrental.utils;

import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.controller.dto.ReservationRequestThymeleaf;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.entity.Reservation;

public class ReservationMapper {

    public static Reservation requestToReservation(ReservationRequest reservationRequest, Car car) {
        return new Reservation(
                reservationRequest.getClient_name(),
                reservationRequest.getEmail(),
                reservationRequest.getAddress(),
                reservationRequest.getPhoneNumber(),
                reservationRequest.getDateFrom(),
                reservationRequest.getDateTo(),
                car
        );
    }

    public static ReservationRequest thymeleafToRequestDTO(ReservationRequestThymeleaf reservation) {
        return new ReservationRequest(
                reservation.getClient_name(),
                reservation.getEmail(),
                reservation.getAddress(),
                reservation.getPhoneNumber(),
                reservation.getDateFrom(),
                reservation.getDateTo(),
                reservation.getCarId()
        );
    }
}
