package com.codecool.carrental.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class NewCarDTO {
    private String name;
    private BigDecimal dalyPrice;
    private String picturePath;
    private boolean isActive;
}
