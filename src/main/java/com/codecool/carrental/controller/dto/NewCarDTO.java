package com.codecool.carrental.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class NewCarDTO {
    @NonNull
    private String name;
    @NonNull
    private BigDecimal dalyPrice;
    @NonNull
    private String picturePath;
    @NonNull
    private boolean isActive;
}
