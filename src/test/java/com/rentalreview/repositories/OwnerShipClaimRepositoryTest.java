package com.rentalreview.repositories;
import com.rentalreview.config.TestBase;
import com.rentalreview.data.OwnershipClaimGenerator;
import com.rentalreview.data.PropertyGenerator;
import com.rentalreview.data.UserGenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OwnerShipClaimRepositoryTest extends TestBase {

    @Autowired
    OwnershipClaimRepository ownershipClaimRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach

    public void cleanUp(){
        ownershipClaimRepository.deleteAll();
        propertyRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateAOwnerShipClaimSuccessfully() {
        var user = UserGenerator.randomUser();
        var property = PropertyGenerator.randomProperty(user);

        userRepository.save(user);
        propertyRepository.save(property);

        ownershipClaimRepository.save(OwnershipClaimGenerator.randomOwnerShipClaim(user,property));

        var ownershipclaims = ownershipClaimRepository.findAll();
        assertThat(ownershipclaims).hasSize(1);
    }
}
