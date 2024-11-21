package com.example.orm_ttps.service;

import com.example.orm_ttps.dto.user.RegisterRequest;
import com.example.orm_ttps.exception.DniAlreadyExistsException;
import com.example.orm_ttps.exception.EmailAlreadyExistsException;
import com.example.orm_ttps.exception.ResourceNotFoundException;
import com.example.orm_ttps.model.*;
import com.example.orm_ttps.repository.RoleRepository;
import com.example.orm_ttps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }

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

    public User update(Long id, RegisterRequest request) throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equals(loggedInEmail)){
            throw new AccessDeniedException("You are not allowed to update this user");
        }

        if (!request.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if (!request.getDni().equals(user.getDni()) && userRepository.existsByDni(request.getDni())) {
            throw new DniAlreadyExistsException(request.getDni());
        }
        Role role = roleRepository.findByName(request.getRole().toUpperCase()).orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + request.getName()));
        user.setDni(request.getDni());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoto_url(request.getPhoto_url());
        user.setRole(role);
        return userRepository.save(user);
    }
}
