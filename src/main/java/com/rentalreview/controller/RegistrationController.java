package com.rentalreview.controller;

import com.rentalreview.dto.UserDto;
import com.rentalreview.entities.User;
import com.rentalreview.services.RegistrationRequest;
import com.rentalreview.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@RestController
@RequestMapping(path = "api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody RegistrationRequest request) {
        User registeredUser;
        try {
            registeredUser = registrationService.register(request);

            //convert the above into this
            var userDto = new UserDto();

            return ResponseEntity.ok(userDto);
        } catch (DuplicateKeyException e) {
            return badRequest().build();
        } catch (Exception e) {
            return internalServerError().build();
        }
    }
}
