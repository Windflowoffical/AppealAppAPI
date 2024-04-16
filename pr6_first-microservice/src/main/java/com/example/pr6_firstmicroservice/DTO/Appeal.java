package com.example.pr6_firstmicroservice.DTO;

import com.example.pr6_firstmicroservice.Models.User;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appeal {

    private String description;

    private User user;

}
