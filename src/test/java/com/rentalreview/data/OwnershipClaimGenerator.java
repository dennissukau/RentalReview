package com.rentalreview.data;

import com.rentalreview.entities.OwnershipClaim;
import com.rentalreview.entities.Property;
import com.rentalreview.entities.User;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class OwnershipClaimGenerator {
    public static OwnershipClaim randomOwnerShipClaim(User user, Property property) {
        return OwnershipClaim.builder()
                .owner(user)
                .status("complete")
                .verifiedAt(LocalDateTime.now())
                .submittedAt(LocalDateTime.now())
                .documentUrl(randomAlphanumeric(15))
                .property(property)
                .build();
    }
}

