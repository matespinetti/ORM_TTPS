package com.example.orm_ttps.objetos_sistema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workers")
@Data
@AllArgsConstructor
public class Worker extends User {

}