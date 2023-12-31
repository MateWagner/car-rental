package com.codecool.carrental.dev;

import com.codecool.carrental.entity.Account;
import com.codecool.carrental.entity.Car;
import com.codecool.carrental.entity.Reservation;
import com.codecool.carrental.repository.AccountRepository;
import com.codecool.carrental.repository.CarRepository;
import com.codecool.carrental.repository.ReservationRepository;
import com.codecool.carrental.security.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DatabaseInit implements CommandLineRunner {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Car car1 = new Car(
                "car1",
                BigDecimal.valueOf(50),
                "/images/stock.jpg",
                true,
                new HashSet<>()
        );
        Car car2 = new Car(
                "car2",
                BigDecimal.valueOf(50),
                "/images/stock.jpg",
                true,
                new HashSet<>()
        );
        carRepository.saveAll(List.of(car1, car2));

        Reservation reservation1 = new Reservation(
                "Kis Istvan",
                "kis@mail.com",
                "1145 Budapest Erno utca 11",
                "+36000000000",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                car1
        );

        Reservation reservation2 = new Reservation(
                "Nagy Istvan",
                "nagy@mail.com",
                "1145 Budapest Erno utca 12",
                "+36000000001",
                LocalDate.now().plusDays(3),
                LocalDate.now().plusDays(4),
                car2
        );

        reservationRepository.saveAll(List.of(reservation1, reservation2));

        Account adminAccount = new Account("admin@mail.com", passwordEncoder.encode("password"), Roles.ADMIN);
        accountRepository.save(adminAccount);
    }
}
