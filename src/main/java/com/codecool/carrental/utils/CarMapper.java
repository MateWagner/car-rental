package com.codecool.carrental.utils;

import com.codecool.carrental.controller.dto.NewCarDTO;
import com.codecool.carrental.entity.Car;

import java.util.HashSet;

public class CarMapper {

    public static Car NewcarDTOToCar(NewCarDTO carDTO) {
        return new Car(
                carDTO.getName(),
                carDTO.getDalyPrice(),
                carDTO.getPicturePath(),
                carDTO.isActive(),
                new HashSet<>()
        );
    }
}
