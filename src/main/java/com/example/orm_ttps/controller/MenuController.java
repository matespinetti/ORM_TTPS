package com.example.orm_ttps.controller;

import com.example.orm_ttps.dto.menu.MenuAddRequest;
import com.example.orm_ttps.model.Menu;
import com.example.orm_ttps.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @GetMapping
    @PreAuthorize("hasAuthority('MENU_READ')")
    public ResponseEntity<List<Menu>> getAll() {
        return ResponseEntity.ok(menuService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MENU_CREATE')")
    public ResponseEntity<Menu> createMenu(@Valid @RequestBody MenuAddRequest request) {
        return ResponseEntity.ok(menuService.create(request));

        // Create a new menu
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_UPDATE')")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @Valid @RequestBody MenuAddRequest request) {
        return ResponseEntity.ok(menuService.update(id, request));

        // Update a menu
    }

}
