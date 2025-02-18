package com.example.orm_ttps.service;

import com.example.orm_ttps.dto.menu.MenuAddRequest;
import com.example.orm_ttps.exception.EmptyMenuException;
import com.example.orm_ttps.exception.MenuComponentFailedTypeException;
import com.example.orm_ttps.exception.ResourceAlreadyExistsException;
import com.example.orm_ttps.exception.ResourceNotFoundException;
import com.example.orm_ttps.model.Menu;
import com.example.orm_ttps.model.MenuComponent;
import com.example.orm_ttps.model.MenuComponentType;
import com.example.orm_ttps.repository.MenuComponentRepository;
import com.example.orm_ttps.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuComponentRepository menuComponentRepository;

    public Menu create(MenuAddRequest request){
        Menu menu = menuRepository.findByDateAndIsVegetarian(request.getDate(), request.getIsVegetarian()).orElse(null);
        if(menu != null){
            System.out.println(request.getIsVegetarian());
            String type = request.getIsVegetarian() ? "vegetarian" : "non-vegetarian";
            throw new ResourceAlreadyExistsException("A " + type + " menu already exists for the date: " + request.getDate());
        }

        List<MenuComponent> components = new ArrayList<>();
        components.add(validateAndGetComponent(request.getStarter(), MenuComponentType.STARTER));
        components.add(validateAndGetComponent(request.getMainDish(), MenuComponentType.MAIN_DISH));
        components.add(validateAndGetComponent(request.getDessert(), MenuComponentType.DESSERT));
        components.add(validateAndGetComponent(request.getDrink(), MenuComponentType.DRINK));

        components = components.stream().filter(Objects::nonNull).toList();
        if (components.isEmpty()) {
            throw new EmptyMenuException("The menu must have at least one component");
        }

        System.out.println(components.size());
        menu = new Menu(request.getName(), request.getPrice(), request.getStock(), request.getIsVegetarian(), request.getDate());
        for (MenuComponent component : components) {
            menu.addComponent(component);
        }

        return menuRepository.save(menu);




    }

    public Menu update(Long id, MenuAddRequest request){
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));

        Menu existingMenu = menuRepository.findByDateAndIsVegetarian(request.getDate(), request.getIsVegetarian()).orElse(null);
        if(existingMenu != null && !existingMenu.getId().equals(id)){
            String type = request.getIsVegetarian() ? "vegetarian" : "non-vegetarian";
            throw new ResourceAlreadyExistsException("A " + type + " menu already exists for the date: " + request.getDate());
        }

        List<MenuComponent> components = new ArrayList<>();
        components.add(validateAndGetComponent(request.getStarter(), MenuComponentType.STARTER));
        components.add(validateAndGetComponent(request.getMainDish(), MenuComponentType.MAIN_DISH));
        components.add(validateAndGetComponent(request.getDessert(), MenuComponentType.DESSERT));
        components.add(validateAndGetComponent(request.getDrink(), MenuComponentType.DRINK));




        components = components.stream().filter(Objects::nonNull).toList();
        if (components.isEmpty()) {
            throw new EmptyMenuException("The menu must have at least one component");
        }
        System.out.println("HOLAAAAAAAAAA");
        menu.setName(request.getName());
        menu.setPrice(request.getPrice());
        menu.setStock(request.getStock());
        menu.setVegetarian(request.getIsVegetarian());
        menu.setDate(request.getDate());
        menu.clearComponents();
        components.forEach(component -> menu.addComponent(component));

        return menuRepository.save(menu);
    }


    public void delete(Long id){
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
        menuRepository.delete(menu);
    }

    private MenuComponent validateAndGetComponent(Long componentId, MenuComponentType expectedType) {
        if (componentId != null) {
            // Find the component by ID
            MenuComponent component = menuComponentRepository.findById(componentId).orElse(null);
            if (component == null) {
                throw new ResourceNotFoundException("MenuComponent not found with id: " + componentId);
            }

            // Check if the component type matches the expected type
            if (component.getType() != expectedType) {
                throw new MenuComponentFailedTypeException("MenuComponent with id: " + componentId + " is not a " + expectedType);
            }

            return component;  // Return the valid component
        }
        return null;  // If componentId is null, return null so it can be skipped in the list
    }

    @Transactional
    public List<Menu> getAll(){
        List<Menu> menus = menuRepository.findAll();
        return menus;
    }



}
