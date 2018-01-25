package com.diviso.infrastructure.service.impl;

import com.diviso.infrastructure.service.BuildingService;
import com.diviso.infrastructure.domain.Building;
import com.diviso.infrastructure.repository.BuildingRepository;
import com.diviso.infrastructure.service.dto.BuildingDTO;
import com.diviso.infrastructure.service.mapper.BuildingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Building.
 */
@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

    private final Logger log = LoggerFactory.getLogger(BuildingServiceImpl.class);

    private final BuildingRepository buildingRepository;

    private final BuildingMapper buildingMapper;

    public BuildingServiceImpl(BuildingRepository buildingRepository, BuildingMapper buildingMapper) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
    }

    /**
     * Save a building.
     *
     * @param buildingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BuildingDTO save(BuildingDTO buildingDTO) {
        log.debug("Request to save Building : {}", buildingDTO);
        Building building = buildingMapper.toEntity(buildingDTO);
        building = buildingRepository.save(building);
        return buildingMapper.toDto(building);
    }

    /**
     * Get all the buildings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuildingDTO> findAll() {
        log.debug("Request to get all Buildings");
        return buildingRepository.findAll().stream()
            .map(buildingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one building by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BuildingDTO findOne(Long id) {
        log.debug("Request to get Building : {}", id);
        Building building = buildingRepository.findOne(id);
        return buildingMapper.toDto(building);
    }

    /**
     * Delete the building by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Building : {}", id);
        buildingRepository.delete(id);
    }
}
