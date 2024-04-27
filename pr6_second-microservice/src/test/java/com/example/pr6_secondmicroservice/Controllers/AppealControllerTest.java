package com.example.pr6_secondmicroservice.Controllers;

import com.example.pr6_secondmicroservice.Models.Appeal;
import com.example.pr6_secondmicroservice.Models.Status;
import com.example.pr6_secondmicroservice.Models.UserDTO;
import com.example.pr6_secondmicroservice.Repositories.AppealRepository;
import com.example.pr6_secondmicroservice.Services.AppealServiceClient;
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
class AppealControllerTest {

    @Mock
    AppealRepository appealRepository;

    @Mock
    AppealServiceClient appealServiceClient;

    @InjectMocks
    AppealController controller;


    @Test
    void testUpdateStatus() {
        //given
        Optional<Appeal> appeal = Optional.ofNullable(Appeal.builder().id(1L).description("Need help!")
                .user(UserDTO.builder().id(1L).nickname("Windflaw").build())
                .status(Status.ACCEPTED_FOR_WORK).build());
        Appeal appeal_for_db = Appeal.builder().id(1L).description("Need help!")
                .user(UserDTO.builder().id(1L).nickname("Windflaw").build())
                .status(Status.DONE).build();
        Mockito.doReturn(appeal).when(this.appealRepository).findById(1L);
        //when
        var responseEntity = this.controller.UpdateStatus(1L, appeal_for_db);
        Mockito.verify(this.appealRepository, Mockito.times(3)).findById(1L);
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("Статус заявки успешно обновлён", responseEntity.getBody());
    }

    @Test
    void testCreateAppeal_AppealServiceClient() {
        //given
        Appeal appeal = Appeal.builder().description("Нужна помощь!").user(UserDTO.builder().id(1L).build()).build();
        UserDTO userindb = UserDTO.builder().id(1L).build();
        //when
        var responseEntity = this.controller.CreateAppeal(appeal);
        Mockito.verify(this.appealServiceClient).GetUserById(userindb.getId());
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void testGetAppealById() {
        //given
        Optional<Appeal> appeal = Optional.ofNullable(Appeal.builder().id(1L).description("Need help!")
                .user(UserDTO.builder().id(1L).nickname("Windflaw").build())
                .status(Status.ACCEPTED_FOR_WORK).build());
        Mockito.doReturn(appeal).when(this.appealRepository).findById(1L);
        //when
        var responseEntity = this.controller.GetAppealById(1L);
        Mockito.verify(this.appealRepository, Mockito.times(2)).findById(1L);
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(appeal, responseEntity.getBody());
    }

    @Test
    void testGetAllAppeal() {
        //given
        var appeals = List.of(
                Appeal.builder().id(1L).description("Need help!")
                        .user(UserDTO.builder().id(1L).nickname("Windflaw").build())
                        .status(Status.ACCEPTED_FOR_WORK),
                Appeal.builder().id(2L).description("Помогите, пожалуйста!")
                        .user(UserDTO.builder().id(2L).nickname("Valikov Kirill").build())
                        .status(Status.ACCEPTED_FOR_WORK));
        Mockito.doReturn(appeals).when(this.appealRepository).findAll();
        //when
        var responseEntity = this.controller.GetAllAppeal();
        Mockito.verify(this.appealRepository).findAll();
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(appeals, responseEntity.getBody());

    }

    @Test
    void testGetAllAppealsByUserId() {
        //given
        var all_appeals = List.of(
                Appeal.builder().id(1L).description("Need help!")
                        .user(UserDTO.builder().id(1L).nickname("Windflaw").build())
                        .status(Status.ACCEPTED_FOR_WORK),
                Appeal.builder().id(2L).description("Помогите, пожалуйста!")
                        .user(UserDTO.builder().id(2L).nickname("Valikov Kirill").build())
                        .status(Status.ACCEPTED_FOR_WORK),
                Appeal.builder().id(3L).description("Нужна срочная помощь!")
                        .user(UserDTO.builder().id(1L).nickname("Windflaw").build())
                        .status(Status.ACCEPTED_FOR_WORK));
        var first_user_appeals = List.of(
                Appeal.builder().id(1L).description("Need help!")
                        .user(UserDTO.builder().id(1L).nickname("Windflaw").build())
                        .status(Status.ACCEPTED_FOR_WORK),
                Appeal.builder().id(3L).description("Нужна срочная помощь!")
                        .user(UserDTO.builder().id(1L).nickname("Windflaw").build())
                        .status(Status.ACCEPTED_FOR_WORK));
        Mockito.doReturn(first_user_appeals).when(this.appealRepository).findByUserId(1L);
        //when
        var responseEntity = this.controller.GetAllAppealsByUserId(1L);
        Mockito.verify(this.appealRepository).findByUserId(1L);
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(first_user_appeals, responseEntity.getBody());
    }
}