package com.example.orm_ttps.repository;

import com.example.orm_ttps.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByDateAndIsVegetarian(LocalDate date, boolean isVegetarian);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.components")
    List<Menu> findAllWithComponents();


    List<Menu> findAllByOrderByDateDesc();

    List<Menu> findAllByDate(LocalDate date);
}
