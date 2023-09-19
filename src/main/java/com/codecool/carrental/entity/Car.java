package com.codecool.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "cars")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @SequenceGenerator(
            name = "car_id_sequence",
            sequenceName = "car_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "car_id_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(
            name = "car_name",
            nullable = false
    )
    private String name;

    @Column(
            name = "daly_price",
            nullable = false
    )
    private BigDecimal dalyPrice;

    @Column(
            name = "picture_path"
    )
    private String picturePath;

    @Column(
            name = "is_active",
            nullable = false
    )
    private boolean isActive;

    @OneToMany(mappedBy = "car")
    private Set<Reservation> reservations;

    public Car(String name, BigDecimal dalyPrice, String picturePath, boolean isActive, Set<Reservation> reservations) {
        this.name = name;
        this.dalyPrice = dalyPrice;
        this.picturePath = picturePath;
        this.isActive = isActive;
        this.reservations = reservations;
    }
}

