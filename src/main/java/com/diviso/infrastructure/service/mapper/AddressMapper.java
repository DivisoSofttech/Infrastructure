package com.diviso.infrastructure.service.mapper;

import com.diviso.infrastructure.domain.*;
import com.diviso.infrastructure.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {CityMapper.class, OrganisationMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "organisation.id", target = "organisationId")
    AddressDTO toDto(Address address);

    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "organisationId", target = "organisation")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
