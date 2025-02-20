package com.example.orm_ttps.controller;

import com.example.orm_ttps.dto.menu_component.MenuComponentAddRequest;
import com.example.orm_ttps.model.MenuComponent;
import com.example.orm_ttps.service.MenuComponentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('COMPONENT_CREATE')")
    public ResponseEntity<MenuComponent> create(@Valid @ModelAttribute MenuComponentAddRequest request, @RequestPart(value = "image", required = false) MultipartFile image){
        MenuComponent menuComponent = menuComponentService.create(request, image);
        return ResponseEntity.ok(menuComponent);

    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('COMPONENT_UPDATE')")
    public ResponseEntity<MenuComponent> update(@PathVariable Long id, @Valid @ModelAttribute MenuComponentAddRequest request, @RequestPart(value = "image", required = false) MultipartFile image){
        MenuComponent menuComponent = menuComponentService.update(id, request, image    );
        return ResponseEntity.ok(menuComponent);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COMPONENT_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuComponentService.delete(id);
        return ResponseEntity.ok().build();    }

}
