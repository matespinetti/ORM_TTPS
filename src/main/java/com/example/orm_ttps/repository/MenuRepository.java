package com.example.orm_ttps.repository;

import com.example.orm_ttps.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByDateAndIsVegetarian(Date date, boolean isVegetarian);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.components")
    List<Menu> findAllWithComponents();
}
