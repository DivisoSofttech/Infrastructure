package com.diviso.infrastructure.service.impl;

import com.diviso.infrastructure.service.FloorService;
import com.diviso.infrastructure.domain.Floor;
import com.diviso.infrastructure.repository.FloorRepository;
import com.diviso.infrastructure.service.dto.FloorDTO;
import com.diviso.infrastructure.service.mapper.FloorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Floor.
 */
@Service
@Transactional
public class FloorServiceImpl implements FloorService {

    private final Logger log = LoggerFactory.getLogger(FloorServiceImpl.class);

    private final FloorRepository floorRepository;

    private final FloorMapper floorMapper;

    public FloorServiceImpl(FloorRepository floorRepository, FloorMapper floorMapper) {
        this.floorRepository = floorRepository;
        this.floorMapper = floorMapper;
    }

    /**
     * Save a floor.
     *
     * @param floorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FloorDTO save(FloorDTO floorDTO) {
        log.debug("Request to save Floor : {}", floorDTO);
        Floor floor = floorMapper.toEntity(floorDTO);
        floor = floorRepository.save(floor);
        return floorMapper.toDto(floor);
    }

    /**
     * Get all the floors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FloorDTO> findAll() {
        log.debug("Request to get all Floors");
        return floorRepository.findAll().stream()
            .map(floorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one floor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FloorDTO findOne(Long id) {
        log.debug("Request to get Floor : {}", id);
        Floor floor = floorRepository.findOne(id);
        return floorMapper.toDto(floor);
    }

    /**
     * Delete the floor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Floor : {}", id);
        floorRepository.delete(id);
    }

	@Override
	@Transactional(readOnly=true)
	public FloorDTO findByName(String name) {
		// TODO Auto-generated method stub
		log.debug("Request to get Floor : {}", name);
		Floor floor = floorRepository.findByName(name);
		return floorMapper.toDto(floor);
	}
}
