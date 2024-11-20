package com.example.orm_ttps.service;

import com.example.orm_ttps.dto.food.FoodAddRequest;
import com.example.orm_ttps.exception.ResourceNotFoundException;
import com.example.orm_ttps.model.Food;
import com.example.orm_ttps.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public Food create(FoodAddRequest request){
        Food food = new Food();
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setStock(request.getStock());
        food.setImage_url(request.getImage_url());
        return foodRepository.save(food);
    }

    public Food update(Long id, FoodAddRequest request){
        Food food = foodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setStock(request.getStock());
        food.setImage_url(request.getImage_url());
        return foodRepository.save(food);
    }

    public List<Food> getAll(){
        return foodRepository.findAll();
    }
}
