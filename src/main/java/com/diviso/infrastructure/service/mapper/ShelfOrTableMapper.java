package com.diviso.infrastructure.service.mapper;

import com.diviso.infrastructure.domain.*;
import com.diviso.infrastructure.service.dto.ShelfOrTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShelfOrTable and its DTO ShelfOrTableDTO.
 */
@Mapper(componentModel = "spring", uses = {SectionMapper.class})
public interface ShelfOrTableMapper extends EntityMapper<ShelfOrTableDTO, ShelfOrTable> {

    @Mapping(source = "section.id", target = "sectionId")
    ShelfOrTableDTO toDto(ShelfOrTable shelfOrTable);

    @Mapping(source = "sectionId", target = "section")
    @Mapping(target = "racksOrChairs", ignore = true)
    ShelfOrTable toEntity(ShelfOrTableDTO shelfOrTableDTO);

    default ShelfOrTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShelfOrTable shelfOrTable = new ShelfOrTable();
        shelfOrTable.setId(id);
        return shelfOrTable;
    }
}
