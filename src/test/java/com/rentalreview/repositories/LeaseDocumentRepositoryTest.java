package com.rentalreview.repositories;

import com.rentalreview.config.TestBase;
import com.rentalreview.data.LeaseDocumentGenerator;
import com.rentalreview.data.PropertyGenerator;
import com.rentalreview.data.UserGenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LeaseDocumentRepositoryTest extends TestBase {

    @Autowired
    LeaseDocumentRepository leaseDocumentRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanUp()
    {
        leaseDocumentRepository.deleteAll();
        propertyRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateALeaseDocumentSuccessfully() {
        var user = UserGenerator.randomUser();
        var property = PropertyGenerator.randomProperty(user);

        userRepository.save(user);
        propertyRepository.save(property);

        leaseDocumentRepository.save(LeaseDocumentGenerator.randomLeaseDocument(user,property));

        var leaseDocuments = leaseDocumentRepository.findAll();
        assertThat(leaseDocuments).hasSize(1);
    }
}
