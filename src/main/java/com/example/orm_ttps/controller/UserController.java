package com.example.orm_ttps.controller;

import com.example.orm_ttps.dto.user.RegisterRequest;
import com.example.orm_ttps.dto.user.UserInfoDetails;
import com.example.orm_ttps.model.User;
import com.example.orm_ttps.service.JwtService;
import com.example.orm_ttps.service.UserInfoService;
import com.example.orm_ttps.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;


    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }


    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @Valid @RequestBody RegisterRequest request) throws AccessDeniedException {

        User user = userService.update(id, request);

        UserDetails userDetails = userInfoService.loadUserByUsername(user.getEmail());

        List<String> permissions = userDetails.getAuthorities().stream().map(Object::toString).toList();

        String role = ((UserInfoDetails) userDetails).getRole();

        String new_access_token = jwtService.generateToken(user.getEmail(), permissions, role);
        String new_refresh_token = jwtService.generateRefreshToken(user.getEmail(), permissions, role);

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", new_access_token);
        response.put("refresh_token", new_refresh_token);
        return ResponseEntity.ok(response);
    }

}
