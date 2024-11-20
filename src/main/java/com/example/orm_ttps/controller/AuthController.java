package com.example.orm_ttps.controller;

import com.example.orm_ttps.dto.user.AuthRequest;
import com.example.orm_ttps.dto.user.RefreshRequest;
import com.example.orm_ttps.dto.user.RegisterRequest;
import com.example.orm_ttps.model.User;
import com.example.orm_ttps.service.JwtService;
import com.example.orm_ttps.service.UserInfoService;
import com.example.orm_ttps.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authentication.isAuthenticated()){
            String token = jwtService.generateToken(request.getEmail());
            String refresh_token = jwtService.generateRefreshToken(request.getEmail());
            return ResponseEntity.ok(Map.of("token", token, "refresh_token", refresh_token));
        } else {
            throw new UsernameNotFoundException("Usuario o contraseña incorrectos");
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshRequest request){
        String refresh_token = request.getRefresh_token();
        String username = jwtService.extractUsername(refresh_token);
        UserDetails userDetails = userInfoService.loadUserByUsername(username);
        if (jwtService.validateToken(refresh_token, userDetails)){
            String new_access_token = jwtService.generateToken(username);
            String new_refresh_token = jwtService.generateRefreshToken(username);
            return ResponseEntity.ok(Map.of("token", new_access_token, "refresh_token", new_refresh_token));

        } else {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request){
        User user = userService.register(request);
        return ResponseEntity.ok(user);
    }

}
