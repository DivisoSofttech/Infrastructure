package com.diviso.infrastructure.service;

import com.diviso.infrastructure.service.dto.RackOrChairDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RackOrChairDTO> findAll(Pageable pageable);

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
