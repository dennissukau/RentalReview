package com.rentalreview.repositories;

import com.rentalreview.config.TestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rentalreview.data.UserGenerator.randomUser;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest extends TestBase {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateAUserSuccessfully() {

        var user = randomUser();

        userRepository.save(user);

        var users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo(user.getUsername());
    }
}