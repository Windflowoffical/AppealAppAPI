package com.example.pr6_firstmicroservice.Controllers;

import com.example.pr6_firstmicroservice.DTO.AppealDTO;
import com.example.pr6_firstmicroservice.Models.User;
import com.example.pr6_firstmicroservice.Repositories.UserRepository;
import com.example.pr6_firstmicroservice.Services.UserServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {


    @Mock
    UserRepository userRepository;

    @Mock
    UserServiceClient userServiceClient;

    @InjectMocks
    UserController controller;


    @Test
    void testCreateAppeal_UserServiceClient() {
        //given
        AppealDTO appealDTO = AppealDTO.builder().description("Нужна помощь!").user(User.builder().id(1L).build()).build();
        //when
        var responseEntity_controller = this.controller.CreateAppeal(appealDTO);
        Mockito.verify(this.userServiceClient).CreateAppeal(appealDTO);
        //then
        assertNotNull(responseEntity_controller);
        assertEquals(HttpStatus.OK, responseEntity_controller.getStatusCode());
        assertEquals("Всё прошло успешно!", responseEntity_controller.getBody());
    }


    @Test
    void testCreateUser() {
        //given
        User user = User.builder().id(1L).email("k.valikov@mail.ru").build();
        Mockito.doReturn(user).when(this.userRepository).save(user);
        //when
        var responseEntity = this.controller.CreateUser(user);
        Mockito.verify(this.userRepository).findByEmail(user.getEmail());
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void testGetUserById() {
        //given
        Optional<User> user = Optional.ofNullable(User.builder().id(1L).email("k.valikov@mail.ru").build());
        Mockito.doReturn(user).when(this.userRepository).findById(1L);
        //when
        var responseEntity = this.controller.GetUserById(1L);
        Mockito.verify(this.userRepository, Mockito.times(2)).findById(1L);
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void testGetAllUsers() {
        //given
        var users = List.of(User.builder().id(1L).email("k.valikov@mail.ru"), User.builder().id(2L).email("k.val@mail.ru"));
        Mockito.doReturn(users).when(this.userRepository).findAll();
        //when
        var responseEntity = this.controller.GetAllUsers();
        Mockito.verify(this.userRepository).findAll();
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(users, responseEntity.getBody());
    }
}