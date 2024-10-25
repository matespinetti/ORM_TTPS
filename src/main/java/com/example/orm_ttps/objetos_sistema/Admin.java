package com.example.orm_ttps.objetos_sistema;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@AllArgsConstructor
public class Admin extends User {

}