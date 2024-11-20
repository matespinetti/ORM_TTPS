package com.example.orm_ttps.controller;

import com.example.orm_ttps.model.Permission;
import com.example.orm_ttps.model.Role;
import com.example.orm_ttps.repository.PermissionRepository;
import com.example.orm_ttps.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seed")
public class SeedController {

    @Autowired
    private PermissionRepository permissionRepository;


    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    private String seed(){
        List<Permission> permissions = List.of(
                new Permission("USER_CREATE"),
                new Permission("USER_UPDATE"),
                new Permission("USER_DELETE"),
                new Permission("USER_READ"),
                new Permission("MENU_CREATE"),
                new Permission("MENU_UPDATE"),
                new Permission("MENU_DELETE"),
                new Permission("MENU_READ"),
                new Permission("FOOD_CREATE"),
                new Permission("FOOD_UPDATE"),
                new Permission("FOOD_DELETE"),
                new Permission("FOOD_READ"),
                new Permission("COMPONENT_CREATE"),
                new Permission("COMPONENT_UPDATE"),
                new Permission("COMPONENT_DELETE"),
                new Permission("COMPONENT_READ")
        );

        List<Permission> savedPermissions = permissionRepository.saveAll(permissions);


        Role client = new Role("CLIENT");
        client.setPermissions(List.of(
                savedPermissions.stream().filter(p -> p.getName().equals("USER_UPDATE")).findFirst().orElseThrow(),
                savedPermissions.stream().filter(p -> p.getName().equals("MENU_READ")).findFirst().orElseThrow(),
                savedPermissions.stream().filter(p -> p.getName().equals("FOOD_READ")).findFirst().orElseThrow(),
                savedPermissions.stream().filter(p -> p.getName().equals("COMPONENT_READ")).findFirst().orElseThrow()
        ));

        Role admin = new Role("ADMIN");
        admin.setPermissions(permissions);


        Role worker = new Role("WORKER");
        worker.setPermissions(List.of(
                savedPermissions.stream().filter(p -> p.getName().equals("USER_READ")).findFirst().orElseThrow(),
                savedPermissions.stream().filter(p -> p.getName().equals("MENU_READ")).findFirst().orElseThrow(),
                savedPermissions.stream().filter(p -> p.getName().equals("FOOD_READ")).findFirst().orElseThrow(),
                savedPermissions.stream().filter(p -> p.getName().equals("COMPONENT_READ")).findFirst().orElseThrow()
        ));


        roleRepository.saveAll(List.of(client, admin, worker));




        return "Seeded";
        
        
    }
}
