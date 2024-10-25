package com.example.orm_ttps.objetos_sistema;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends Product {


    @ManyToOne(optional = true)
    private MenuComponent starter;

    @ManyToOne(optional = true)
    private MenuComponent mainCourse;

    @ManyToOne(optional = true)
    private MenuComponent dessert;

    @ManyToOne(optional = true)
    private MenuComponent drink;

    @Column(nullable = false)
    private boolean isVegetarian;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;


}