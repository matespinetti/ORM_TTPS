package com.example.orm_ttps.objetos_sistema;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "menu_components")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String imageUrl;



    public MenuComponent(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}