package com.diviso.infrastructure.service.mapper;

import com.diviso.infrastructure.domain.*;
import com.diviso.infrastructure.service.dto.BuildingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Building and its DTO BuildingDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganisationMapper.class})
public interface BuildingMapper extends EntityMapper<BuildingDTO, Building> {

    @Mapping(source = "organisation.id", target = "organisationId")
    BuildingDTO toDto(Building building);

    @Mapping(source = "organisationId", target = "organisation")
    @Mapping(target = "floors", ignore = true)
    Building toEntity(BuildingDTO buildingDTO);

    default Building fromId(Long id) {
        if (id == null) {
            return null;
        }
        Building building = new Building();
        building.setId(id);
        return building;
    }
}
