package com.diviso.infrastructure.service;

import com.diviso.infrastructure.service.dto.CityDTO;
import java.util.List;

/**
 * Service Interface for managing City.
 */
public interface CityService {

    /**
     * Save a city.
     *
     * @param cityDTO the entity to save
     * @return the persisted entity
     */
    CityDTO save(CityDTO cityDTO);

    /**
     * Get all the cities.
     *
     * @return the list of entities
     */
    List<CityDTO> findAll();

    /**
     * Get the "id" city.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CityDTO findOne(Long id);

    /**
     * Delete the "id" city.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
