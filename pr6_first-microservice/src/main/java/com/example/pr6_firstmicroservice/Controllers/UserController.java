package com.example.pr6_firstmicroservice.Controllers;

import com.example.pr6_firstmicroservice.DTO.AppealDTO;
import com.example.pr6_firstmicroservice.Exceptions.BadRequestException;
import com.example.pr6_firstmicroservice.Models.User;
import com.example.pr6_firstmicroservice.Repositories.UserRepository;
import com.example.pr6_firstmicroservice.Services.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @PostMapping("/users/create_appeal")
    public ResponseEntity<String> CreateAppeal(@RequestBody AppealDTO appealDTO) {
        userServiceClient.CreateAppeal(appealDTO);
        return ResponseEntity.ok().body("Всё прошло успешно!");
    }


    @PostMapping("/users/create")
    public ResponseEntity<?> CreateUser(@RequestBody User userfordb) {
        userRepository.findByEmail(userfordb.getEmail()).ifPresentOrElse(user -> {
            throw new BadRequestException(String.format("Пользователь с таким email '%s' уже существует!",
                    userfordb.getEmail()));
        }, () -> {
            userRepository.save(userfordb);
        });
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userfordb);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody User userfordb)
    {
        userRepository.findByEmailAndPassword(userfordb.getEmail(), userfordb.getPassword()).
                ifPresentOrElse((user) -> {
                    System.out.println("Пользователь успешно авторизован!");
                }, () -> {
                    throw new BadRequestException(String.format("Пользователя с такими данными не существует: %s, %s",
                            userfordb.getEmail(), userfordb.getPassword()));
                });
        return ResponseEntity.ok().body(userRepository.findByEmailAndPassword(userfordb.getEmail(), userfordb.getPassword()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> GetUserById(@PathVariable Long id) {
        if(userRepository.findById(id).isPresent()) {
            Optional<User> userfromdb = userRepository.findById(id);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userfromdb);
        } else {
            return ResponseEntity.ok().body("Пользователя с таким id = " + id + " не существует!");
        }
    }

    @GetMapping("/users/get_all")
    public ResponseEntity<?> GetAllUsers() {
        List<User> all_users = userRepository.findAll();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(all_users);
    }

}
