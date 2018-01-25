package com.diviso.infrastructure.service;

import com.diviso.infrastructure.service.dto.OrganisationDTO;
import java.util.List;

/**
 * Service Interface for managing Organisation.
 */
public interface OrganisationService {

    /**
     * Save a organisation.
     *
     * @param organisationDTO the entity to save
     * @return the persisted entity
     */
    OrganisationDTO save(OrganisationDTO organisationDTO);

    /**
     * Get all the organisations.
     *
     * @return the list of entities
     */
    List<OrganisationDTO> findAll();

    /**
     * Get the "id" organisation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrganisationDTO findOne(Long id);

    /**
     * Delete the "id" organisation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
