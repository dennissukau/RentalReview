package com.rentalreview.services;

import com.rentalreview.config.TestBase;
import com.rentalreview.data.UserGenerator;
import com.rentalreview.entities.User;
import com.rentalreview.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @AfterEach
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    void shouldLoadUserByUsername() {
        User testUser = UserGenerator.randomUser();
        userRepository.save(testUser);

        var userToFind = userService.loadUserByUsername(testUser.getEmail());

        assertThat(userToFind).isEqualTo(testUser);
    }

    @Test
    void shouldSignUpUser() {
        //Need to add elements later
        User testUser = UserGenerator.randomUser();

        User result = userService.signUpUser(testUser);
        Optional<User> newUser = userRepository.findByEmail(testUser.getEmail());

        assertThat(newUser).isPresent();
        assertEquals(testUser.getEmail(), newUser.get().getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserExists(){
        User user = UserGenerator.randomUser();
        userRepository.save(user);

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPasswordHash(user.getPasswordHash());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.signUpUser(newUser);
        });

        assertEquals("User with this email already exists", exception.getMessage());

    }
}
