package com.example.orm_ttps.service;

import com.example.orm_ttps.dto.user.RegisterRequest;
import com.example.orm_ttps.exception.DniAlreadyExistsException;
import com.example.orm_ttps.exception.EmailAlreadyExistsException;
import com.example.orm_ttps.exception.ResourceNotFoundException;
import com.example.orm_ttps.model.*;
import com.example.orm_ttps.repository.RoleRepository;
import com.example.orm_ttps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User register (RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if (userRepository.existsByDni(request.getDni())) {
            throw new DniAlreadyExistsException(request.getDni());
        }

        Role role = roleRepository.findByName(request.getRole().toUpperCase()).orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + request.getName()));

        User user = new User();

        user.setDni(request.getDni());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoto_url(request.getPhoto_url());
        user.setRole(role);


        return userRepository.save(user);



    }

    public User update(Long id, RegisterRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setDni(request.getDni());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoto_url(request.getPhoto_url());
        return userRepository.save(user);
    }
}
