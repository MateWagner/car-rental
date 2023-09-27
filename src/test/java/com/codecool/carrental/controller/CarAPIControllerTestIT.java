package com.codecool.carrental.controller;

import com.codecool.carrental.controller.dto.RequestedPeriod;
import com.codecool.carrental.controller.dto.ReservationRequest;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.exception.BadRequestException;
import com.codecool.carrental.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(CarAPIController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarAPIControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CarService carService;


    @Test
    void getAvailableCarsInPeriod() throws Exception {
        RequestedPeriod requestedPeriod = new RequestedPeriod(
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        List<Car> cars = List.of(new Car());

        when(carService.getAvailableCarsInPeriod(any(LocalDate.class), any(LocalDate.class))).thenReturn(cars);
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/car/available")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestedPeriod)))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(
                objectMapper.writeValueAsString(cars),
                response.getContentAsString()
        );

        verify(carService, times(1)).getAvailableCarsInPeriod(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void getAvailableCarsInPeriodAndBadRequest() throws Exception {
        RequestedPeriod requestedPeriod = new RequestedPeriod(
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        );

        when(carService.getAvailableCarsInPeriod(any(LocalDate.class), any(LocalDate.class))).thenThrow(BadRequestException.class);
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/car/available")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestedPeriod)))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        verify(carService, times(1)).getAvailableCarsInPeriod(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void createReservation() throws Exception {
        ReservationRequest reservationRequest = new ReservationRequest(
                "name",
                "email@mail.com",
                "adress",
                "phone",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                1L
        );

        when(carService.reserveCar(reservationRequest)).thenReturn(1L);
        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/v1/car/reserve")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservationRequest)))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(
                objectMapper.writeValueAsString(1L),
                response.getContentAsString()
        );

        verify(carService, times(1)).reserveCar(reservationRequest);
    }

    @Test
    void createReservationBadRequest() throws Exception {
        ReservationRequest reservationRequest = new ReservationRequest(
                "name",
                "email@mail.com",
                "adress",
                "phone",
                LocalDate.now(),
                LocalDate.now(),
                1L
        );

        when(carService.reserveCar(reservationRequest)).thenThrow(BadRequestException.class);
        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/v1/car/reserve")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservationRequest)))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        verify(carService, times(1)).reserveCar(reservationRequest);
    }
}
