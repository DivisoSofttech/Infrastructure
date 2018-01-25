package com.diviso.infrastructure.service;

import com.diviso.infrastructure.service.dto.FloorDTO;
import java.util.List;

/**
 * Service Interface for managing Floor.
 */
public interface FloorService {

    /**
     * Save a floor.
     *
     * @param floorDTO the entity to save
     * @return the persisted entity
     */
    FloorDTO save(FloorDTO floorDTO);

    /**
     * Get all the floors.
     *
     * @return the list of entities
     */
    List<FloorDTO> findAll();

    /**
     * Get the "id" floor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FloorDTO findOne(Long id);

    /**
     * Delete the "id" floor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	FloorDTO findByName(String name);
}
