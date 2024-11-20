package com.example.orm_ttps.controller;

import com.example.orm_ttps.dto.food.FoodAddRequest;
import com.example.orm_ttps.model.Food;
import com.example.orm_ttps.model.Product;
import com.example.orm_ttps.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private FoodService foodService;


    @GetMapping
    @PreAuthorize("hasAuthority('FOOD_READ')")
    public ResponseEntity<List<Food>> getAll(){
        return ResponseEntity.ok(foodService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FOOD_CREATE')")
    public ResponseEntity<Food> create(@Valid @RequestBody FoodAddRequest request){
        Food food = foodService.create(request);
        return ResponseEntity.ok(food);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('FOOD_UPDATE')")
    public ResponseEntity<Food> update(@PathVariable Long id, @Valid @RequestBody FoodAddRequest request){
        Food food = foodService.update(id, request);
        return ResponseEntity.ok(food);
    }




}
