package com.diviso.infrastructure.web.rest;

import com.diviso.infrastructure.InfrastructureApp;

import com.diviso.infrastructure.domain.ShelfOrTable;
import com.diviso.infrastructure.repository.ShelfOrTableRepository;
import com.diviso.infrastructure.service.ShelfOrTableService;
import com.diviso.infrastructure.service.dto.ShelfOrTableDTO;
import com.diviso.infrastructure.service.mapper.ShelfOrTableMapper;
import com.diviso.infrastructure.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.diviso.infrastructure.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShelfOrTableResource REST controller.
 *
 * @see ShelfOrTableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfrastructureApp.class)
public class ShelfOrTableResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ShelfOrTableRepository shelfOrTableRepository;

    @Autowired
    private ShelfOrTableMapper shelfOrTableMapper;

    @Autowired
    private ShelfOrTableService shelfOrTableService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShelfOrTableMockMvc;

    private ShelfOrTable shelfOrTable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShelfOrTableResource shelfOrTableResource = new ShelfOrTableResource(shelfOrTableService);
        this.restShelfOrTableMockMvc = MockMvcBuilders.standaloneSetup(shelfOrTableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShelfOrTable createEntity(EntityManager em) {
        ShelfOrTable shelfOrTable = new ShelfOrTable()
            .name(DEFAULT_NAME);
        return shelfOrTable;
    }

    @Before
    public void initTest() {
        shelfOrTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createShelfOrTable() throws Exception {
        int databaseSizeBeforeCreate = shelfOrTableRepository.findAll().size();

        // Create the ShelfOrTable
        ShelfOrTableDTO shelfOrTableDTO = shelfOrTableMapper.toDto(shelfOrTable);
        restShelfOrTableMockMvc.perform(post("/api/shelf-or-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shelfOrTableDTO)))
            .andExpect(status().isCreated());

        // Validate the ShelfOrTable in the database
        List<ShelfOrTable> shelfOrTableList = shelfOrTableRepository.findAll();
        assertThat(shelfOrTableList).hasSize(databaseSizeBeforeCreate + 1);
        ShelfOrTable testShelfOrTable = shelfOrTableList.get(shelfOrTableList.size() - 1);
        assertThat(testShelfOrTable.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createShelfOrTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shelfOrTableRepository.findAll().size();

        // Create the ShelfOrTable with an existing ID
        shelfOrTable.setId(1L);
        ShelfOrTableDTO shelfOrTableDTO = shelfOrTableMapper.toDto(shelfOrTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShelfOrTableMockMvc.perform(post("/api/shelf-or-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shelfOrTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShelfOrTable in the database
        List<ShelfOrTable> shelfOrTableList = shelfOrTableRepository.findAll();
        assertThat(shelfOrTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShelfOrTables() throws Exception {
        // Initialize the database
        shelfOrTableRepository.saveAndFlush(shelfOrTable);

        // Get all the shelfOrTableList
        restShelfOrTableMockMvc.perform(get("/api/shelf-or-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shelfOrTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getShelfOrTable() throws Exception {
        // Initialize the database
        shelfOrTableRepository.saveAndFlush(shelfOrTable);

        // Get the shelfOrTable
        restShelfOrTableMockMvc.perform(get("/api/shelf-or-tables/{id}", shelfOrTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shelfOrTable.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShelfOrTable() throws Exception {
        // Get the shelfOrTable
        restShelfOrTableMockMvc.perform(get("/api/shelf-or-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShelfOrTable() throws Exception {
        // Initialize the database
        shelfOrTableRepository.saveAndFlush(shelfOrTable);
        int databaseSizeBeforeUpdate = shelfOrTableRepository.findAll().size();

        // Update the shelfOrTable
        ShelfOrTable updatedShelfOrTable = shelfOrTableRepository.findOne(shelfOrTable.getId());
        // Disconnect from session so that the updates on updatedShelfOrTable are not directly saved in db
        em.detach(updatedShelfOrTable);
        updatedShelfOrTable
            .name(UPDATED_NAME);
        ShelfOrTableDTO shelfOrTableDTO = shelfOrTableMapper.toDto(updatedShelfOrTable);

        restShelfOrTableMockMvc.perform(put("/api/shelf-or-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shelfOrTableDTO)))
            .andExpect(status().isOk());

        // Validate the ShelfOrTable in the database
        List<ShelfOrTable> shelfOrTableList = shelfOrTableRepository.findAll();
        assertThat(shelfOrTableList).hasSize(databaseSizeBeforeUpdate);
        ShelfOrTable testShelfOrTable = shelfOrTableList.get(shelfOrTableList.size() - 1);
        assertThat(testShelfOrTable.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingShelfOrTable() throws Exception {
        int databaseSizeBeforeUpdate = shelfOrTableRepository.findAll().size();

        // Create the ShelfOrTable
        ShelfOrTableDTO shelfOrTableDTO = shelfOrTableMapper.toDto(shelfOrTable);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShelfOrTableMockMvc.perform(put("/api/shelf-or-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shelfOrTableDTO)))
            .andExpect(status().isCreated());

        // Validate the ShelfOrTable in the database
        List<ShelfOrTable> shelfOrTableList = shelfOrTableRepository.findAll();
        assertThat(shelfOrTableList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShelfOrTable() throws Exception {
        // Initialize the database
        shelfOrTableRepository.saveAndFlush(shelfOrTable);
        int databaseSizeBeforeDelete = shelfOrTableRepository.findAll().size();

        // Get the shelfOrTable
        restShelfOrTableMockMvc.perform(delete("/api/shelf-or-tables/{id}", shelfOrTable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShelfOrTable> shelfOrTableList = shelfOrTableRepository.findAll();
        assertThat(shelfOrTableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShelfOrTable.class);
        ShelfOrTable shelfOrTable1 = new ShelfOrTable();
        shelfOrTable1.setId(1L);
        ShelfOrTable shelfOrTable2 = new ShelfOrTable();
        shelfOrTable2.setId(shelfOrTable1.getId());
        assertThat(shelfOrTable1).isEqualTo(shelfOrTable2);
        shelfOrTable2.setId(2L);
        assertThat(shelfOrTable1).isNotEqualTo(shelfOrTable2);
        shelfOrTable1.setId(null);
        assertThat(shelfOrTable1).isNotEqualTo(shelfOrTable2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShelfOrTableDTO.class);
        ShelfOrTableDTO shelfOrTableDTO1 = new ShelfOrTableDTO();
        shelfOrTableDTO1.setId(1L);
        ShelfOrTableDTO shelfOrTableDTO2 = new ShelfOrTableDTO();
        assertThat(shelfOrTableDTO1).isNotEqualTo(shelfOrTableDTO2);
        shelfOrTableDTO2.setId(shelfOrTableDTO1.getId());
        assertThat(shelfOrTableDTO1).isEqualTo(shelfOrTableDTO2);
        shelfOrTableDTO2.setId(2L);
        assertThat(shelfOrTableDTO1).isNotEqualTo(shelfOrTableDTO2);
        shelfOrTableDTO1.setId(null);
        assertThat(shelfOrTableDTO1).isNotEqualTo(shelfOrTableDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shelfOrTableMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shelfOrTableMapper.fromId(null)).isNull();
    }
}
