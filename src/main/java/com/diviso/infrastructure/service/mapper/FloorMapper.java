package com.diviso.infrastructure.service.mapper;

import com.diviso.infrastructure.domain.*;
import com.diviso.infrastructure.service.dto.FloorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Floor and its DTO FloorDTO.
 */
@Mapper(componentModel = "spring", uses = {BuildingMapper.class})
public interface FloorMapper extends EntityMapper<FloorDTO, Floor> {

    @Mapping(source = "building.id", target = "buildingId")
    FloorDTO toDto(Floor floor);

    @Mapping(source = "buildingId", target = "building")
    @Mapping(target = "sections", ignore = true)
    Floor toEntity(FloorDTO floorDTO);

    default Floor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Floor floor = new Floor();
        floor.setId(id);
        return floor;
    }
}
