package com.codecool.carrental.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequest {
    private final String client_name;
    private final String email;
    private final String address;
    private final String phoneNumber;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final Long carId;
}
