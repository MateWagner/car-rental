package com.codecool.carrental.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ReservationRequestThymeleaf {
    private String client_name;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Long carId;
}
