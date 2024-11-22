package com.example.orm_ttps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = true)
    private String photo_url;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Suggestion> suggestions;


    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User(String dni, String name, String surname, String email, String password, String photo_url, Role role) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.photo_url = photo_url;
        this.role = role;
    }




}