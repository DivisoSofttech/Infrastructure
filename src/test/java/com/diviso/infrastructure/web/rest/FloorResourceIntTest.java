package com.diviso.infrastructure.web.rest;

import com.diviso.infrastructure.InfrastructureApp;

import com.diviso.infrastructure.domain.Floor;
import com.diviso.infrastructure.repository.FloorRepository;
import com.diviso.infrastructure.service.FloorService;
import com.diviso.infrastructure.service.dto.FloorDTO;
import com.diviso.infrastructure.service.mapper.FloorMapper;
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
 * Test class for the FloorResource REST controller.
 *
 * @see FloorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfrastructureApp.class)
public class FloorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private FloorService floorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFloorMockMvc;

    private Floor floor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FloorResource floorResource = new FloorResource(floorService);
        this.restFloorMockMvc = MockMvcBuilders.standaloneSetup(floorResource)
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
    public static Floor createEntity(EntityManager em) {
        Floor floor = new Floor()
            .name(DEFAULT_NAME);
        return floor;
    }

    @Before
    public void initTest() {
        floor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFloor() throws Exception {
        int databaseSizeBeforeCreate = floorRepository.findAll().size();

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);
        restFloorMockMvc.perform(post("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isCreated());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate + 1);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFloorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = floorRepository.findAll().size();

        // Create the Floor with an existing ID
        floor.setId(1L);
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFloorMockMvc.perform(post("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFloors() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        // Get all the floorList
        restFloorMockMvc.perform(get("/api/floors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(floor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        // Get the floor
        restFloorMockMvc.perform(get("/api/floors/{id}", floor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(floor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFloor() throws Exception {
        // Get the floor
        restFloorMockMvc.perform(get("/api/floors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Update the floor
        Floor updatedFloor = floorRepository.findOne(floor.getId());
        // Disconnect from session so that the updates on updatedFloor are not directly saved in db
        em.detach(updatedFloor);
        updatedFloor
            .name(UPDATED_NAME);
        FloorDTO floorDTO = floorMapper.toDto(updatedFloor);

        restFloorMockMvc.perform(put("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isOk());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFloorMockMvc.perform(put("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isCreated());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);
        int databaseSizeBeforeDelete = floorRepository.findAll().size();

        // Get the floor
        restFloorMockMvc.perform(delete("/api/floors/{id}", floor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Floor.class);
        Floor floor1 = new Floor();
        floor1.setId(1L);
        Floor floor2 = new Floor();
        floor2.setId(floor1.getId());
        assertThat(floor1).isEqualTo(floor2);
        floor2.setId(2L);
        assertThat(floor1).isNotEqualTo(floor2);
        floor1.setId(null);
        assertThat(floor1).isNotEqualTo(floor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FloorDTO.class);
        FloorDTO floorDTO1 = new FloorDTO();
        floorDTO1.setId(1L);
        FloorDTO floorDTO2 = new FloorDTO();
        assertThat(floorDTO1).isNotEqualTo(floorDTO2);
        floorDTO2.setId(floorDTO1.getId());
        assertThat(floorDTO1).isEqualTo(floorDTO2);
        floorDTO2.setId(2L);
        assertThat(floorDTO1).isNotEqualTo(floorDTO2);
        floorDTO1.setId(null);
        assertThat(floorDTO1).isNotEqualTo(floorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(floorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(floorMapper.fromId(null)).isNull();
    }
}
