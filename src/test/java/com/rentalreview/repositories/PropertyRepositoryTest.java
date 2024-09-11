package com.rentalreview.repositories;

import com.rentalreview.config.TestBase;
import com.rentalreview.data.PropertyGenerator;
import com.rentalreview.data.UserGenerator;
import com.rentalreview.entities.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PropertyRepositoryTest extends TestBase {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        propertyRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateAPropertySuccessfully() {
        var user = UserGenerator.randomUser();
        userRepository.save(user);

        var property = PropertyGenerator.randomProperty(user);
        propertyRepository.save(property);

        var properties = propertyRepository.findAll();
        assertThat(properties).hasSize(1);
        assertThat(properties.get(0).getOwner().getUsername()).isEqualTo(user.getUsername());
    }

}

