package com.example.orm_ttps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food extends Product {

    @Column(nullable = true)
    private String image_url;


    public Food(String name, double price, int stock, String image_url) {
        super(name, price, stock);
        this.image_url = image_url;
    }


}