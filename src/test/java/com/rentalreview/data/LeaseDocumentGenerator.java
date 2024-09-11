package com.rentalreview.data;

import com.rentalreview.entities.LeaseDocument;
import com.rentalreview.entities.Property;
import com.rentalreview.entities.User;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;


public class LeaseDocumentGenerator {
    public static LeaseDocument randomLeaseDocument(User user, Property property) {
        return LeaseDocument.builder()
                .documentUrl(randomAlphanumeric(15))
                .tenant(user)
                .createdTimestamp(LocalDateTime.now())
                .property(property)
                .build();
    }
}
