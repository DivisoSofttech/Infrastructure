package com.diviso.infrastructure.service;

import com.diviso.infrastructure.service.dto.RackOrChairDTO;
import java.util.List;

/**
 * Service Interface for managing RackOrChair.
 */
public interface RackOrChairService {

    /**
     * Save a rackOrChair.
     *
     * @param rackOrChairDTO the entity to save
     * @return the persisted entity
     */
    RackOrChairDTO save(RackOrChairDTO rackOrChairDTO);

    /**
     * Get all the rackOrChairs.
     *
     * @return the list of entities
     */
    List<RackOrChairDTO> findAll();

    /**
     * Get the "id" rackOrChair.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RackOrChairDTO findOne(Long id);

    /**
     * Delete the "id" rackOrChair.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
