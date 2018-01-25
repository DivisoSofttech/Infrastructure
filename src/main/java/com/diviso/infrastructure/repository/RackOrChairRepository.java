package com.diviso.infrastructure.repository;

import com.diviso.infrastructure.domain.RackOrChair;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RackOrChair entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RackOrChairRepository extends JpaRepository<RackOrChair, Long> {

}
