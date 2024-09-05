package com.rentalreview;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String username;
    private String passwordHash;
    private String role;
    private LocalDateTime createdAt = LocalDateTime.now();

}
