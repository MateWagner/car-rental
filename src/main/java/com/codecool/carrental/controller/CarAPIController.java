package com.codecool.carrental.controller;

import com.codecool.carrental.controller.dto.RequestedPeriod;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarAPIController {
    private final CarService carService;

    @GetMapping("available")
    public List<Car> getAvailableCarsInPeriod(@RequestBody RequestedPeriod requestedTimes) {
        return carService.getAvailableCarsInPeriod(requestedTimes);
    }
}
