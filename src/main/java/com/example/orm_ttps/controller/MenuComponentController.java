package com.example.orm_ttps.controller;

import com.example.orm_ttps.dto.menu_component.MenuComponentAddRequest;
import com.example.orm_ttps.model.MenuComponent;
import com.example.orm_ttps.service.MenuComponentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-components")
public class MenuComponentController {
    @Autowired
    private MenuComponentService menuComponentService;

    @GetMapping
    public ResponseEntity<List<MenuComponent>> getAll(){
        return ResponseEntity.ok(menuComponentService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('COMPONENT_CREATE')")
    public ResponseEntity<MenuComponent> create(@Valid @RequestBody MenuComponentAddRequest request){
        MenuComponent menuComponent = menuComponentService.create(request);
        return ResponseEntity.ok(menuComponent);

    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('COMPONENT_UPDATE')")
    public ResponseEntity<MenuComponent> update(@PathVariable Long id, @Valid @RequestBody MenuComponentAddRequest request){
        MenuComponent menuComponent = menuComponentService.update(id, request);
        return ResponseEntity.ok(menuComponent);
    }

}
