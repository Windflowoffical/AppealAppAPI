package com.example.pr6_firstmicroservice.Controllers;

import com.example.pr6_firstmicroservice.DTO.Appeal;
import com.example.pr6_firstmicroservice.Exceptions.BadRequestException;
import com.example.pr6_firstmicroservice.Models.User;
import com.example.pr6_firstmicroservice.Repositories.UserRepository;
import com.example.pr6_firstmicroservice.Services.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> CreateAppeal(@RequestBody Appeal appeal) {
        userServiceClient.CreateAppeal(appeal);
        return ResponseEntity.ok().body("Всё прошло успешно!");
    }


    @PostMapping("/users/create")
    public ResponseEntity<?> CreateUser(@RequestBody User userfordb) {
        userRepository.findByNickname(userfordb.getNickname()).ifPresentOrElse(user -> {
            throw new BadRequestException(String.format("Пользователь с таким никнеймом '%s' уже существует!",
                    userfordb.getNickname()));
        }, () -> {
            userRepository.save(userfordb);
        });
        return ResponseEntity.ok().body(userfordb);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> GetUserById(@PathVariable Long id) {
        if(userRepository.findById(id).isPresent()) {
            Optional<User> userfromdb = userRepository.findById(id);
            return ResponseEntity.ok().body(userfromdb);
        } else {
            return ResponseEntity.ok().body("Пользователя с таким id = " + id + " не существует!");
        }
    }

    @GetMapping("/users/get_all")
    public ResponseEntity<?> GetAllUsers() {
        List<User> all_users = userRepository.findAll();
        return ResponseEntity.ok().body(all_users);
    }

}
