package com.example.orm_ttps.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;


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
    @NotNull
    private String name;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private MenuComponentType type;


    @ManyToMany
    @JoinTable(
            name = "menu_component_menu",
            joinColumns = @JoinColumn(name = "menu_component_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<Menu> menus;




}

