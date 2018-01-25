package com.diviso.infrastructure.service.impl;

import com.diviso.infrastructure.service.RackOrChairService;
import com.diviso.infrastructure.domain.RackOrChair;
import com.diviso.infrastructure.repository.RackOrChairRepository;
import com.diviso.infrastructure.service.dto.RackOrChairDTO;
import com.diviso.infrastructure.service.mapper.RackOrChairMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RackOrChair.
 */
@Service
@Transactional
public class RackOrChairServiceImpl implements RackOrChairService {

    private final Logger log = LoggerFactory.getLogger(RackOrChairServiceImpl.class);

    private final RackOrChairRepository rackOrChairRepository;

    private final RackOrChairMapper rackOrChairMapper;

    public RackOrChairServiceImpl(RackOrChairRepository rackOrChairRepository, RackOrChairMapper rackOrChairMapper) {
        this.rackOrChairRepository = rackOrChairRepository;
        this.rackOrChairMapper = rackOrChairMapper;
    }

    /**
     * Save a rackOrChair.
     *
     * @param rackOrChairDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RackOrChairDTO save(RackOrChairDTO rackOrChairDTO) {
        log.debug("Request to save RackOrChair : {}", rackOrChairDTO);
        RackOrChair rackOrChair = rackOrChairMapper.toEntity(rackOrChairDTO);
        rackOrChair = rackOrChairRepository.save(rackOrChair);
        return rackOrChairMapper.toDto(rackOrChair);
    }

    /**
     * Get all the rackOrChairs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RackOrChairDTO> findAll() {
        log.debug("Request to get all RackOrChairs");
        return rackOrChairRepository.findAll().stream()
            .map(rackOrChairMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one rackOrChair by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RackOrChairDTO findOne(Long id) {
        log.debug("Request to get RackOrChair : {}", id);
        RackOrChair rackOrChair = rackOrChairRepository.findOne(id);
        return rackOrChairMapper.toDto(rackOrChair);
    }

    /**
     * Delete the rackOrChair by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RackOrChair : {}", id);
        rackOrChairRepository.delete(id);
    }
}
