package com.codecool.carrental.controller.dto;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
public class RequestedPeriod {
    @NonNull
    private final LocalDate dateFrom;
    @NonNull
    private final LocalDate dateTo;

}
