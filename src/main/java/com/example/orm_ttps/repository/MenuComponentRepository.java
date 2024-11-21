package com.example.orm_ttps.repository;

import com.example.orm_ttps.model.MenuComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuComponentRepository extends JpaRepository<MenuComponent, Long>{
    Optional<MenuComponent> findByName(String name);
}
