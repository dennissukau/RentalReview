package com.rentalreview.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity {
    private LocalDateTime createdTimestamp;
}
