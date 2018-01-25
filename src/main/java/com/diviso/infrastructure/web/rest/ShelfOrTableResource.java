package com.diviso.infrastructure.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.infrastructure.service.ShelfOrTableService;
import com.diviso.infrastructure.web.rest.errors.BadRequestAlertException;
import com.diviso.infrastructure.web.rest.util.HeaderUtil;
import com.diviso.infrastructure.web.rest.util.PaginationUtil;
import com.diviso.infrastructure.service.dto.ShelfOrTableDTO;
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
 * REST controller for managing ShelfOrTable.
 */
@RestController
@RequestMapping("/api")
public class ShelfOrTableResource {

    private final Logger log = LoggerFactory.getLogger(ShelfOrTableResource.class);

    private static final String ENTITY_NAME = "shelfOrTable";

    private final ShelfOrTableService shelfOrTableService;

    public ShelfOrTableResource(ShelfOrTableService shelfOrTableService) {
        this.shelfOrTableService = shelfOrTableService;
    }

    /**
     * POST  /shelf-or-tables : Create a new shelfOrTable.
     *
     * @param shelfOrTableDTO the shelfOrTableDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shelfOrTableDTO, or with status 400 (Bad Request) if the shelfOrTable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shelf-or-tables")
    @Timed
    public ResponseEntity<ShelfOrTableDTO> createShelfOrTable(@RequestBody ShelfOrTableDTO shelfOrTableDTO) throws URISyntaxException {
        log.debug("REST request to save ShelfOrTable : {}", shelfOrTableDTO);
        if (shelfOrTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new shelfOrTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShelfOrTableDTO result = shelfOrTableService.save(shelfOrTableDTO);
        return ResponseEntity.created(new URI("/api/shelf-or-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shelf-or-tables : Updates an existing shelfOrTable.
     *
     * @param shelfOrTableDTO the shelfOrTableDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shelfOrTableDTO,
     * or with status 400 (Bad Request) if the shelfOrTableDTO is not valid,
     * or with status 500 (Internal Server Error) if the shelfOrTableDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shelf-or-tables")
    @Timed
    public ResponseEntity<ShelfOrTableDTO> updateShelfOrTable(@RequestBody ShelfOrTableDTO shelfOrTableDTO) throws URISyntaxException {
        log.debug("REST request to update ShelfOrTable : {}", shelfOrTableDTO);
        if (shelfOrTableDTO.getId() == null) {
            return createShelfOrTable(shelfOrTableDTO);
        }
        ShelfOrTableDTO result = shelfOrTableService.save(shelfOrTableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shelfOrTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shelf-or-tables : get all the shelfOrTables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shelfOrTables in body
     */
    @GetMapping("/shelf-or-tables")
    @Timed
    public ResponseEntity<List<ShelfOrTableDTO>> getAllShelfOrTables(Pageable pageable) {
        log.debug("REST request to get a page of ShelfOrTables");
        Page<ShelfOrTableDTO> page = shelfOrTableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shelf-or-tables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shelf-or-tables/:id : get the "id" shelfOrTable.
     *
     * @param id the id of the shelfOrTableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shelfOrTableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shelf-or-tables/{id}")
    @Timed
    public ResponseEntity<ShelfOrTableDTO> getShelfOrTable(@PathVariable Long id) {
        log.debug("REST request to get ShelfOrTable : {}", id);
        ShelfOrTableDTO shelfOrTableDTO = shelfOrTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shelfOrTableDTO));
    }

    /**
     * DELETE  /shelf-or-tables/:id : delete the "id" shelfOrTable.
     *
     * @param id the id of the shelfOrTableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shelf-or-tables/{id}")
    @Timed
    public ResponseEntity<Void> deleteShelfOrTable(@PathVariable Long id) {
        log.debug("REST request to delete ShelfOrTable : {}", id);
        shelfOrTableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
