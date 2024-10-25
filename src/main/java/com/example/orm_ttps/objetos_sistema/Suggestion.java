// src/main/java/com/example/orm_ttps/objetos_sistema/Suggestion.java
package com.example.orm_ttps.objetos_sistema;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "suggestions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String suggestion;
    @Column(nullable = false)
    private LocalDateTime datetime;


}