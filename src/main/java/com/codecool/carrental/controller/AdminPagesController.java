package com.codecool.carrental.controller;

import com.codecool.carrental.controller.dto.ReservationWithCarNameAndId;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.service.CarService;
import com.codecool.carrental.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminPagesController {
    private final CarService carService;
    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public String getReservation(Model model) {
        List<ReservationWithCarNameAndId> reservations = reservationService.getAllReservationToAdmin();
        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    @PostMapping("/car")
    public String saveCar(Car car) {
        carService.updateCar(car);
        return "redirect:/admin/cars";
    }

    @GetMapping("/cars")
    public String getCars(Model model) {
        List<Car> cars = carService.getAllCarToAdmin();
        model.addAttribute("cars", cars);
        return "cars";
    }

    @GetMapping("/car/{carId}")
    public String editCar(Model model, @PathVariable Long carId) {
        Car car = carService.getCarById(carId);
        model.addAttribute("car", car);
        return "car-form";
    }
}
