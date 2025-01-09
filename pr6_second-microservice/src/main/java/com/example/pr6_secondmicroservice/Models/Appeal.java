package com.example.pr6_secondmicroservice.Models;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "appeal",
        schema = "public"
)
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private UserDTO user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

}

