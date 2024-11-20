package com.example.orm_ttps.repository;

import com.example.orm_ttps.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

}

