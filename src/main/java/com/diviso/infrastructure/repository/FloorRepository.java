package com.diviso.infrastructure.repository;

import com.diviso.infrastructure.domain.Floor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Floor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

	Floor findByName(String name);

}