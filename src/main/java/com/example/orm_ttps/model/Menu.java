package com.example.orm_ttps.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "menus", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "isVegetarian"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends Product {


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_menu_component",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_component_id")
    )

    private List<MenuComponent> components = new ArrayList<>();

    @Column(nullable = false)
    private boolean isVegetarian;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;


    public Menu(String name, double price, int stock, boolean isVegetarian, LocalDate date) {
        super(name, price, stock);
        this.isVegetarian = isVegetarian;
        this.date = date;
    }

    public void addComponent(MenuComponent component) {
        this.components.add(component);
    }

    public void clearComponents() {
        this.components.clear();
    }


}