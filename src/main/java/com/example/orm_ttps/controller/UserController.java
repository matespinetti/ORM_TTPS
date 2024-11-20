package com.example.orm_ttps.controller;

import com.example.orm_ttps.dto.user.RegisterRequest;
import com.example.orm_ttps.model.User;
import com.example.orm_ttps.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('USER_READ')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody RegisterRequest request){
        User user = userService.update(id, request);
        return ResponseEntity.ok(user);
    }

}
