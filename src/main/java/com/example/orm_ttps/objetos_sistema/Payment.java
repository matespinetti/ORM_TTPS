// src/main/java/com/example/orm_ttps/objetos_sistema/Payment.java
package com.example.orm_ttps.objetos_sistema;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime paymentDate;
    @OneToOne()
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;




}