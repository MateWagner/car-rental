package com.codecool.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {

    @Id
    @SequenceGenerator(
            name = "reservation_id_sequence",
            sequenceName = "reservation_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "reservation_id_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(
            name = "client_name",
            nullable = false
    )
    private String clientName;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "address",
            nullable = false
    )
    private String address;

    @Column(
            name = "phone_number",
            nullable = false
    )
    private String phoneNumber;

    @Column(
            name = "date_from",
            nullable = false
    )
    private LocalDate dateFrom;

    @Column(
            name = "date_to",
            nullable = false
    )
    private LocalDate dateTo;

    @ManyToOne
    @JoinColumn(
            name = "car_id",
            foreignKey = @ForeignKey(name = "fk_car_to_reservation")
    )
    private Car car;

    public Reservation(String clientName, String email, String address, String phoneNumber, LocalDate dateFrom, LocalDate dateTo, Car car) {
        this.clientName = clientName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.car = car;
    }
}

