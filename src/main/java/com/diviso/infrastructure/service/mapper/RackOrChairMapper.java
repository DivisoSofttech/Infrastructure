package com.diviso.infrastructure.service.mapper;

import com.diviso.infrastructure.domain.*;
import com.diviso.infrastructure.service.dto.RackOrChairDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RackOrChair and its DTO RackOrChairDTO.
 */
@Mapper(componentModel = "spring", uses = {ShelfOrTableMapper.class})
public interface RackOrChairMapper extends EntityMapper<RackOrChairDTO, RackOrChair> {

    @Mapping(source = "shelfOrTable.id", target = "shelfOrTableId")
    RackOrChairDTO toDto(RackOrChair rackOrChair);

    @Mapping(source = "shelfOrTableId", target = "shelfOrTable")
    RackOrChair toEntity(RackOrChairDTO rackOrChairDTO);

    default RackOrChair fromId(Long id) {
        if (id == null) {
            return null;
        }
        RackOrChair rackOrChair = new RackOrChair();
        rackOrChair.setId(id);
        return rackOrChair;
    }
}
