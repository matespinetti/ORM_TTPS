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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            List<String> permissions = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

            String token = jwtService.generateToken(request.getEmail(), permissions);
            String refresh_token = jwtService.generateRefreshToken(request.getEmail(), permissions);
            return ResponseEntity.ok(Map.of("access_token", token, "refresh_token", refresh_token));
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
            List<String> permissions = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            String new_access_token = jwtService.generateToken(username, permissions);
            String new_refresh_token = jwtService.generateRefreshToken(username, permissions);
            return ResponseEntity.ok(Map.of("access_token", new_access_token, "refresh_token", new_refresh_token));

        } else {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }
    }


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> register(@Valid @ModelAttribute RegisterRequest request, @RequestPart(value = "image", required = false)MultipartFile image){
        if (image!= null && !image.isEmpty()){
            System.out.println("ENTREEE");
            System.out.println("Image received: " + image.getOriginalFilename());

        }
        User user = userService.register(request, image);
        return ResponseEntity.ok(user);
    }

}
