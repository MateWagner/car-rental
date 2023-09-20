package com.codecool.carrental.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PeriodThymeleaf implements Serializable {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    public long getDayBetween() {
        return DAYS.between(dateFrom, dateTo);
    }
}
