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
                .baseUrl("http://firstms:8081/users") //for docker
                //.baseUrl("http://localhost:8080/users") //for local tests
                .build();
    }

    public UserDTO GetUserById(Long id) {
        return restClient.get().uri("/" + id).retrieve().body(UserDTO.class);
    }
}
