package com.example.orm_ttps.dto.menu;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Date;

@Data
public class MenuAddRequest {
    @NotNull
    private String name;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotNull
    private Boolean isVegetarian;

    @NotNull
    private LocalDate date;


    private Long starter;
    private Long mainDish;
    private Long drink;
    private Long dessert;

}
