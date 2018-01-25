package com.diviso.infrastructure.service.impl;

import com.diviso.infrastructure.service.ShelfOrTableService;
import com.diviso.infrastructure.domain.ShelfOrTable;
import com.diviso.infrastructure.repository.ShelfOrTableRepository;
import com.diviso.infrastructure.service.dto.ShelfOrTableDTO;
import com.diviso.infrastructure.service.mapper.ShelfOrTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ShelfOrTable.
 */
@Service
@Transactional
public class ShelfOrTableServiceImpl implements ShelfOrTableService {

    private final Logger log = LoggerFactory.getLogger(ShelfOrTableServiceImpl.class);

    private final ShelfOrTableRepository shelfOrTableRepository;

    private final ShelfOrTableMapper shelfOrTableMapper;

    public ShelfOrTableServiceImpl(ShelfOrTableRepository shelfOrTableRepository, ShelfOrTableMapper shelfOrTableMapper) {
        this.shelfOrTableRepository = shelfOrTableRepository;
        this.shelfOrTableMapper = shelfOrTableMapper;
    }

    /**
     * Save a shelfOrTable.
     *
     * @param shelfOrTableDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShelfOrTableDTO save(ShelfOrTableDTO shelfOrTableDTO) {
        log.debug("Request to save ShelfOrTable : {}", shelfOrTableDTO);
        ShelfOrTable shelfOrTable = shelfOrTableMapper.toEntity(shelfOrTableDTO);
        shelfOrTable = shelfOrTableRepository.save(shelfOrTable);
        return shelfOrTableMapper.toDto(shelfOrTable);
    }

    /**
     * Get all the shelfOrTables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShelfOrTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShelfOrTables");
        return shelfOrTableRepository.findAll(pageable)
            .map(shelfOrTableMapper::toDto);
    }

    /**
     * Get one shelfOrTable by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShelfOrTableDTO findOne(Long id) {
        log.debug("Request to get ShelfOrTable : {}", id);
        ShelfOrTable shelfOrTable = shelfOrTableRepository.findOne(id);
        return shelfOrTableMapper.toDto(shelfOrTable);
    }

    /**
     * Delete the shelfOrTable by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShelfOrTable : {}", id);
        shelfOrTableRepository.delete(id);
    }
}
