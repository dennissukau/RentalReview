package com.rentalreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyDto {
    private Long id;
    private UserDto owner;
    private String address;
    private String city;
    private String propertyType;
}
