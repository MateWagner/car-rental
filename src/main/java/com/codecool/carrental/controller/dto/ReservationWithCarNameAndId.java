package com.codecool.carrental.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationWithCarNameAndId {
    private final Long id;
    private final Long carId;
    private final String carName;
    private final String clientName;
    private final String email;
    private final String address;
    private final String phoneNumber;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
}
