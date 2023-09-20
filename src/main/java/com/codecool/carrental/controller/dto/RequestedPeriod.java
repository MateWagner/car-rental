package com.codecool.carrental.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestedPeriod {
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

}
