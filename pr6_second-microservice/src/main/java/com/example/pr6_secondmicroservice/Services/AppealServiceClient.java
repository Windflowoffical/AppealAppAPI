package com.example.pr6_secondmicroservice.Services;

import com.example.pr6_secondmicroservice.Models.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AppealServiceClient {

    private final RestClient restClient;


    public AppealServiceClient() {
        restClient = RestClient.builder()
                .baseUrl("http://firstms:8081/users") //for docker, 8081 cause env SERVER_PORT = 8081 in docker-compose
                //.baseUrl("http://localhost:8081/users") //for local tests, 8081 cause env SERVER_PORT = 8081 in docker-compose
                .build();
    }

    public UserDTO GetUserById(Long id) {
        return restClient.get().uri("/" + id).retrieve().body(UserDTO.class);
    }
}
