package com.codecool.carrental.controller;

import com.codecool.carrental.controller.dto.PeriodThymeleaf;
import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.controller.dto.ReservationRequestThymeleaf;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.service.CarService;
import com.codecool.carrental.service.ReservationService;
import com.codecool.carrental.utils.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final ReservationService reservationService;

    @GetMapping
    public String getHomePage(Model model) {
        PeriodThymeleaf period = new PeriodThymeleaf();
        model.addAttribute("period", period);
        return "index";
    }

    @PostMapping("/availablecar")
    public String getAvailableCarInPeriod(Model model, @ModelAttribute("period") PeriodThymeleaf period) {
        List<Car> cars = carService.getAllCar();
        model.addAttribute("cars", cars);
        model.addAttribute("period", period);
        return "result";
    }

    @GetMapping("/order/{carId}")
    public String placeOrder(Model model, @PathVariable Long carId, @RequestParam("from") String fromStr, @RequestParam("to") String toStr) {
        ReservationRequestThymeleaf reservationRequest = new ReservationRequestThymeleaf();
        Car car = carService.getCarById(carId);
        PeriodThymeleaf period = new PeriodThymeleaf(
                LocalDate.parse(fromStr),
                LocalDate.parse(toStr)
        );

        reservationRequest.setDateFrom(period.getDateFrom());
        reservationRequest.setDateTo(period.getDateTo());
        reservationRequest.setCarId(car.getId());
        model.addAttribute("req", reservationRequest);
        model.addAttribute("period", period);
        model.addAttribute("car", car);

        return "order";
    }

    @PostMapping("place-order")
    public String saveOrder(@ModelAttribute("req") ReservationRequestThymeleaf reservationRequest) {
        System.out.println(reservationRequest.getClient_name());
        System.out.println(reservationRequest.getPhoneNumber());
        System.out.println(reservationRequest.getCarId());
        System.out.println(reservationRequest.getDateFrom());
        System.out.println(reservationRequest.getDateTo());
        ReservationRequest request = ReservationMapper.thymeleafToRequestDTO(reservationRequest);
        carService.reserveCar(request);
        return "redirect:/";
    }
}
