package com.example.orm_ttps.dto.menu_component;

import com.example.orm_ttps.model.MenuComponentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MenuComponentAddRequest {
    @NotNull(message = "El nombre es requerido")
    private String name;



    @NotNull(message = "El tipo es requerido")
    @Pattern(regexp = "STARTER|MAIN_DISH|DRINK|DESSERT", message = "El tipo debe ser STARTER, MAIN_DISH, DRINK o DESSERT")
    private String type;



}
