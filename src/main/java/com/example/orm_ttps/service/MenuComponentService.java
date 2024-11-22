package com.example.orm_ttps.service;

import com.example.orm_ttps.dto.menu_component.MenuComponentAddRequest;
import com.example.orm_ttps.exception.ResourceAlreadyExistsException;
import com.example.orm_ttps.exception.ResourceNotFoundException;
import com.example.orm_ttps.model.MenuComponent;
import com.example.orm_ttps.model.MenuComponentType;
import com.example.orm_ttps.repository.MenuComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuComponentService  {
    @Autowired
    private MenuComponentRepository menuComponentRepository;

    public MenuComponent create(MenuComponentAddRequest request){
        MenuComponent existingComponent = menuComponentRepository.findByName(request.getName()).orElse(null);
        if(existingComponent != null){
            throw new ResourceAlreadyExistsException("Component with name: " + request.getName() + " already exists");
        }
        MenuComponent menuComponent = new MenuComponent(request.getName(), request.getImageUrl(), MenuComponentType.valueOf(request.getType()));
        return menuComponentRepository.save(menuComponent);
    }


    public MenuComponent update(Long id, MenuComponentAddRequest request){
        MenuComponent menuComponent = menuComponentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Component not found with id: " + id));
        MenuComponent existingComponent = menuComponentRepository.findByName(request.getName()).orElse(null);
        if(existingComponent != null && !existingComponent.getId().equals(id)){
            throw new ResourceAlreadyExistsException("Component with name: " + request.getName() + " already exists");
        }
        menuComponent.setName(request.getName());
        menuComponent.setImageUrl(request.getImageUrl());
        menuComponent.setType(MenuComponentType.valueOf(request.getType()));
        return menuComponentRepository.save(menuComponent);
    }

    public List<MenuComponent> getAll(){
        return menuComponentRepository.findAll();
    }


}
