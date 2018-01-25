package com.diviso.infrastructure.repository;

import com.diviso.infrastructure.domain.ShelfOrTable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShelfOrTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShelfOrTableRepository extends JpaRepository<ShelfOrTable, Long> {

}
