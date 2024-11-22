package com.example.orm_ttps.controller;

import com.example.orm_ttps.model.*;
import com.example.orm_ttps.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/seed")
public class SeedController {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private MenuComponentRepository menuComponentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FoodRepository foodRepository;

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

        User user1 = new User("21895643", "felipe", "costas", "felipecostas@gmail.com", passwordEncoder.encode("123456"), "http://photo.jpg", worker);
        User user2 = new User("45283666", "matias", "ramos", "matiasramos@gmail.com", passwordEncoder.encode("123456"), "http://photo.jpg", admin);
        User user3 = new User("44245678", "juan", "perez", "juanperez@gmail.com", passwordEncoder.encode("123456"), "http://photo.jpg", client);

        this.userRepository.saveAll(List.of(user1, user2, user3));


        List<Food> foods = List.of(
                new Food("Empanada de carne", 100, 50, "http://empanadadecarne.jpg"),
                new Food("Milanesa con papas", 200, 30, "http://milanesaconpapas.jpg"),
                new Food("Helado de chocolate", 150, 20, "http://heladochocolate.jpg"),
                new Food("Coca Cola", 50, 100, "http://cocacola.jpg"),
                new Food("Provoleta", 250, 10, "http://provoleta.jpg"),
                new Food("Turron", 300, 5, "http://asado.jpg"),
                new Food("Barra de cereal", 100, 30, "http://flan.jpg")
        );

        foodRepository.saveAll(foods);




        List<MenuComponent> components = List.of(
                new MenuComponent("Empanada", "abc", MenuComponentType.STARTER),
                new MenuComponent("Milanesa", "abc", MenuComponentType.MAIN_DISH),
                new MenuComponent("Helado", "abc", MenuComponentType.DESSERT),
                new MenuComponent("Coca", "abc", MenuComponentType.DRINK),
                new MenuComponent("Provoleta", "abc", MenuComponentType.STARTER),
                new MenuComponent("Asado", "abc", MenuComponentType.MAIN_DISH),
                new MenuComponent("Flan", "abc", MenuComponentType.DESSERT),
                new MenuComponent("Fernet", "abc", MenuComponentType.DRINK)

        );

        this.menuComponentRepository.saveAll(components);


        Menu menu = new Menu("Menu 1", 100.0, 10, false, Date.from(new Date().toInstant().plusSeconds(86400)));
        for (MenuComponent component : components.subList(0,4)) {
            menu.addComponent(component);
        }


        Menu menu2 = new Menu("Menu 2", 200.0, 20, true, new Date());
        for (MenuComponent component : components.subList(4,8)) {
            menu2.addComponent(component);
        }

        this.menuRepository.saveAll(List.of(menu, menu2));




        return "Seeded";
        
        
    }
}
