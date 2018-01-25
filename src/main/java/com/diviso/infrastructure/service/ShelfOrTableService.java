package com.diviso.infrastructure.service;

import com.diviso.infrastructure.service.dto.ShelfOrTableDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShelfOrTable.
 */
public interface ShelfOrTableService {

    /**
     * Save a shelfOrTable.
     *
     * @param shelfOrTableDTO the entity to save
     * @return the persisted entity
     */
    ShelfOrTableDTO save(ShelfOrTableDTO shelfOrTableDTO);

    /**
     * Get all the shelfOrTables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShelfOrTableDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shelfOrTable.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShelfOrTableDTO findOne(Long id);

    /**
     * Delete the "id" shelfOrTable.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
