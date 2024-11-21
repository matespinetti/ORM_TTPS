package com.example.orm_ttps.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "menu_components")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private MenuComponentType type;


    @ManyToMany(mappedBy = "components")
    @JsonIgnore

    private List<Menu> menus = new ArrayList<>();


    public MenuComponent(String name, String imageUrl, MenuComponentType type) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
    }




}

