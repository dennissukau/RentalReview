package com.rentalreview.services;

import com.rentalreview.EmailValidator.EmailValidator;
import com.rentalreview.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final UserService userService;

    public User register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.isValid(request.getEmail());

        if (!isValidEmail) {
            throw new DuplicateKeyException("Email %s not valid".formatted(request.getEmail()));
        }

        var newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .passwordHash(request.getPassword())
                .role("Tenant")
                .build();

        return userService.signUpUser(newUser);
    }
}
