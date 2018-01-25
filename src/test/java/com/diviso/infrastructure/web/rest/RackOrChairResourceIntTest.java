package com.diviso.infrastructure.web.rest;

import com.diviso.infrastructure.InfrastructureApp;

import com.diviso.infrastructure.domain.RackOrChair;
import com.diviso.infrastructure.repository.RackOrChairRepository;
import com.diviso.infrastructure.service.RackOrChairService;
import com.diviso.infrastructure.service.dto.RackOrChairDTO;
import com.diviso.infrastructure.service.mapper.RackOrChairMapper;
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
 * Test class for the RackOrChairResource REST controller.
 *
 * @see RackOrChairResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfrastructureApp.class)
public class RackOrChairResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RackOrChairRepository rackOrChairRepository;

    @Autowired
    private RackOrChairMapper rackOrChairMapper;

    @Autowired
    private RackOrChairService rackOrChairService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRackOrChairMockMvc;

    private RackOrChair rackOrChair;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RackOrChairResource rackOrChairResource = new RackOrChairResource(rackOrChairService);
        this.restRackOrChairMockMvc = MockMvcBuilders.standaloneSetup(rackOrChairResource)
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
    public static RackOrChair createEntity(EntityManager em) {
        RackOrChair rackOrChair = new RackOrChair()
            .name(DEFAULT_NAME);
        return rackOrChair;
    }

    @Before
    public void initTest() {
        rackOrChair = createEntity(em);
    }

    @Test
    @Transactional
    public void createRackOrChair() throws Exception {
        int databaseSizeBeforeCreate = rackOrChairRepository.findAll().size();

        // Create the RackOrChair
        RackOrChairDTO rackOrChairDTO = rackOrChairMapper.toDto(rackOrChair);
        restRackOrChairMockMvc.perform(post("/api/rack-or-chairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rackOrChairDTO)))
            .andExpect(status().isCreated());

        // Validate the RackOrChair in the database
        List<RackOrChair> rackOrChairList = rackOrChairRepository.findAll();
        assertThat(rackOrChairList).hasSize(databaseSizeBeforeCreate + 1);
        RackOrChair testRackOrChair = rackOrChairList.get(rackOrChairList.size() - 1);
        assertThat(testRackOrChair.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRackOrChairWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rackOrChairRepository.findAll().size();

        // Create the RackOrChair with an existing ID
        rackOrChair.setId(1L);
        RackOrChairDTO rackOrChairDTO = rackOrChairMapper.toDto(rackOrChair);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRackOrChairMockMvc.perform(post("/api/rack-or-chairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rackOrChairDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RackOrChair in the database
        List<RackOrChair> rackOrChairList = rackOrChairRepository.findAll();
        assertThat(rackOrChairList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRackOrChairs() throws Exception {
        // Initialize the database
        rackOrChairRepository.saveAndFlush(rackOrChair);

        // Get all the rackOrChairList
        restRackOrChairMockMvc.perform(get("/api/rack-or-chairs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rackOrChair.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRackOrChair() throws Exception {
        // Initialize the database
        rackOrChairRepository.saveAndFlush(rackOrChair);

        // Get the rackOrChair
        restRackOrChairMockMvc.perform(get("/api/rack-or-chairs/{id}", rackOrChair.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rackOrChair.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRackOrChair() throws Exception {
        // Get the rackOrChair
        restRackOrChairMockMvc.perform(get("/api/rack-or-chairs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRackOrChair() throws Exception {
        // Initialize the database
        rackOrChairRepository.saveAndFlush(rackOrChair);
        int databaseSizeBeforeUpdate = rackOrChairRepository.findAll().size();

        // Update the rackOrChair
        RackOrChair updatedRackOrChair = rackOrChairRepository.findOne(rackOrChair.getId());
        // Disconnect from session so that the updates on updatedRackOrChair are not directly saved in db
        em.detach(updatedRackOrChair);
        updatedRackOrChair
            .name(UPDATED_NAME);
        RackOrChairDTO rackOrChairDTO = rackOrChairMapper.toDto(updatedRackOrChair);

        restRackOrChairMockMvc.perform(put("/api/rack-or-chairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rackOrChairDTO)))
            .andExpect(status().isOk());

        // Validate the RackOrChair in the database
        List<RackOrChair> rackOrChairList = rackOrChairRepository.findAll();
        assertThat(rackOrChairList).hasSize(databaseSizeBeforeUpdate);
        RackOrChair testRackOrChair = rackOrChairList.get(rackOrChairList.size() - 1);
        assertThat(testRackOrChair.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRackOrChair() throws Exception {
        int databaseSizeBeforeUpdate = rackOrChairRepository.findAll().size();

        // Create the RackOrChair
        RackOrChairDTO rackOrChairDTO = rackOrChairMapper.toDto(rackOrChair);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRackOrChairMockMvc.perform(put("/api/rack-or-chairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rackOrChairDTO)))
            .andExpect(status().isCreated());

        // Validate the RackOrChair in the database
        List<RackOrChair> rackOrChairList = rackOrChairRepository.findAll();
        assertThat(rackOrChairList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRackOrChair() throws Exception {
        // Initialize the database
        rackOrChairRepository.saveAndFlush(rackOrChair);
        int databaseSizeBeforeDelete = rackOrChairRepository.findAll().size();

        // Get the rackOrChair
        restRackOrChairMockMvc.perform(delete("/api/rack-or-chairs/{id}", rackOrChair.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RackOrChair> rackOrChairList = rackOrChairRepository.findAll();
        assertThat(rackOrChairList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RackOrChair.class);
        RackOrChair rackOrChair1 = new RackOrChair();
        rackOrChair1.setId(1L);
        RackOrChair rackOrChair2 = new RackOrChair();
        rackOrChair2.setId(rackOrChair1.getId());
        assertThat(rackOrChair1).isEqualTo(rackOrChair2);
        rackOrChair2.setId(2L);
        assertThat(rackOrChair1).isNotEqualTo(rackOrChair2);
        rackOrChair1.setId(null);
        assertThat(rackOrChair1).isNotEqualTo(rackOrChair2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RackOrChairDTO.class);
        RackOrChairDTO rackOrChairDTO1 = new RackOrChairDTO();
        rackOrChairDTO1.setId(1L);
        RackOrChairDTO rackOrChairDTO2 = new RackOrChairDTO();
        assertThat(rackOrChairDTO1).isNotEqualTo(rackOrChairDTO2);
        rackOrChairDTO2.setId(rackOrChairDTO1.getId());
        assertThat(rackOrChairDTO1).isEqualTo(rackOrChairDTO2);
        rackOrChairDTO2.setId(2L);
        assertThat(rackOrChairDTO1).isNotEqualTo(rackOrChairDTO2);
        rackOrChairDTO1.setId(null);
        assertThat(rackOrChairDTO1).isNotEqualTo(rackOrChairDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rackOrChairMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rackOrChairMapper.fromId(null)).isNull();
    }
}
