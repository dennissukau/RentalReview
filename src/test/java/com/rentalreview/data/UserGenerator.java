package com.rentalreview.data;

import com.rentalreview.entities.User;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class UserGenerator {

    public static User randomUser() {
        var username = randomAlphanumeric(10);
        var firstName = randomAlphanumeric(8);
        var lastName = randomAlphanumeric(10);

        return User.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email("%s@email.com".formatted(username))
                .role("role")
                .passwordHash(randomAlphanumeric(20))
                .createdTimestamp(LocalDateTime.now())
                .build();
    }
}
