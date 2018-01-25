package com.diviso.infrastructure.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.infrastructure.service.RackOrChairService;
import com.diviso.infrastructure.web.rest.errors.BadRequestAlertException;
import com.diviso.infrastructure.web.rest.util.HeaderUtil;
import com.diviso.infrastructure.web.rest.util.PaginationUtil;
import com.diviso.infrastructure.service.dto.RackOrChairDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RackOrChair.
 */
@RestController
@RequestMapping("/api")
public class RackOrChairResource {

    private final Logger log = LoggerFactory.getLogger(RackOrChairResource.class);

    private static final String ENTITY_NAME = "rackOrChair";

    private final RackOrChairService rackOrChairService;

    public RackOrChairResource(RackOrChairService rackOrChairService) {
        this.rackOrChairService = rackOrChairService;
    }

    /**
     * POST  /rack-or-chairs : Create a new rackOrChair.
     *
     * @param rackOrChairDTO the rackOrChairDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rackOrChairDTO, or with status 400 (Bad Request) if the rackOrChair has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rack-or-chairs")
    @Timed
    public ResponseEntity<RackOrChairDTO> createRackOrChair(@RequestBody RackOrChairDTO rackOrChairDTO) throws URISyntaxException {
        log.debug("REST request to save RackOrChair : {}", rackOrChairDTO);
        if (rackOrChairDTO.getId() != null) {
            throw new BadRequestAlertException("A new rackOrChair cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RackOrChairDTO result = rackOrChairService.save(rackOrChairDTO);
        return ResponseEntity.created(new URI("/api/rack-or-chairs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rack-or-chairs : Updates an existing rackOrChair.
     *
     * @param rackOrChairDTO the rackOrChairDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rackOrChairDTO,
     * or with status 400 (Bad Request) if the rackOrChairDTO is not valid,
     * or with status 500 (Internal Server Error) if the rackOrChairDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rack-or-chairs")
    @Timed
    public ResponseEntity<RackOrChairDTO> updateRackOrChair(@RequestBody RackOrChairDTO rackOrChairDTO) throws URISyntaxException {
        log.debug("REST request to update RackOrChair : {}", rackOrChairDTO);
        if (rackOrChairDTO.getId() == null) {
            return createRackOrChair(rackOrChairDTO);
        }
        RackOrChairDTO result = rackOrChairService.save(rackOrChairDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rackOrChairDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rack-or-chairs : get all the rackOrChairs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rackOrChairs in body
     */
    @GetMapping("/rack-or-chairs")
    @Timed
    public ResponseEntity<List<RackOrChairDTO>> getAllRackOrChairs(Pageable pageable) {
        log.debug("REST request to get a page of RackOrChairs");
        Page<RackOrChairDTO> page = rackOrChairService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rack-or-chairs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rack-or-chairs/:id : get the "id" rackOrChair.
     *
     * @param id the id of the rackOrChairDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rackOrChairDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rack-or-chairs/{id}")
    @Timed
    public ResponseEntity<RackOrChairDTO> getRackOrChair(@PathVariable Long id) {
        log.debug("REST request to get RackOrChair : {}", id);
        RackOrChairDTO rackOrChairDTO = rackOrChairService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rackOrChairDTO));
    }

    /**
     * DELETE  /rack-or-chairs/:id : delete the "id" rackOrChair.
     *
     * @param id the id of the rackOrChairDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rack-or-chairs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRackOrChair(@PathVariable Long id) {
        log.debug("REST request to delete RackOrChair : {}", id);
        rackOrChairService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
