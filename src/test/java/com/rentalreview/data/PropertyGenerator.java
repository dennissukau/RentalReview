package com.rentalreview.data;

import com.rentalreview.entities.Property;
import com.rentalreview.entities.User;

import java.time.LocalDateTime;

public class PropertyGenerator {

    public static Property randomProperty(User user) {
        return Property.builder()
                .owner(user)
                .address("123 Main St")
                .city("Brisbane")
                .state("QLD")
                .postcode("4000")
                .propertyType("House")
                .bedrooms(6)
                .bathrooms(2)
                .parkingSpaces(2)
                .squareMeters(250)
                .description("Spacious 4-bedroom house in the heart of Brisbane")
                .createdTimestamp(LocalDateTime.now())
                .build();
    }
}
