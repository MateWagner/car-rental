package com.codecool.carrental.controller.dto;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
public class ReservationRequest {
    @NonNull
    private final String client_name;
    @NonNull
    private final String email;
    @NonNull
    private final String address;
    @NonNull
    private final String phoneNumber;
    @NonNull
    private final LocalDate dateFrom;
    @NonNull
    private final LocalDate dateTo;
    @NonNull
    private final Long carId;
}
