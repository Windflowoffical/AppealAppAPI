package com.example.pr6_firstmicroservice.DTO;

import com.example.pr6_firstmicroservice.Models.User;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppealDTO {

    private String description;

    private User user;

}
