package com.example.orm_ttps.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends Product {


    @ManyToMany(mappedBy = "menus")
    private List<MenuComponent> components;

    @Column(nullable = false)
    private boolean isVegetarian;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;


}