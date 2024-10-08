package com.rentalreview.mapper;

import com.rentalreview.dto.PropertyDto;
import com.rentalreview.entities.Property;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

    PropertyDto propertyToPropertyDto(Property property);

    Property propertyDtoToProperty(PropertyDto propertyDto);
}
