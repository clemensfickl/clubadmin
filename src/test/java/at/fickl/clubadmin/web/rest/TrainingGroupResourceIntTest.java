package at.fickl.clubadmin.web.rest;

import at.fickl.clubadmin.ClubadminApp;

import at.fickl.clubadmin.domain.TrainingGroup;
import at.fickl.clubadmin.repository.TrainingGroupRepository;
import at.fickl.clubadmin.service.TrainingGroupService;
import at.fickl.clubadmin.service.dto.TrainingGroupDTO;
import at.fickl.clubadmin.service.mapper.TrainingGroupMapper;
import at.fickl.clubadmin.web.rest.errors.ExceptionTranslator;

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

import static at.fickl.clubadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TrainingGroupResource REST controller.
 *
 * @see TrainingGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClubadminApp.class)
public class TrainingGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TrainingGroupRepository trainingGroupRepository;

    @Autowired
    private TrainingGroupMapper trainingGroupMapper;

    @Autowired
    private TrainingGroupService trainingGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrainingGroupMockMvc;

    private TrainingGroup trainingGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingGroupResource trainingGroupResource = new TrainingGroupResource(trainingGroupService);
        this.restTrainingGroupMockMvc = MockMvcBuilders.standaloneSetup(trainingGroupResource)
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
    public static TrainingGroup createEntity(EntityManager em) {
        TrainingGroup trainingGroup = new TrainingGroup()
            .name(DEFAULT_NAME);
        return trainingGroup;
    }

    @Before
    public void initTest() {
        trainingGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingGroup() throws Exception {
        int databaseSizeBeforeCreate = trainingGroupRepository.findAll().size();

        // Create the TrainingGroup
        TrainingGroupDTO trainingGroupDTO = trainingGroupMapper.toDto(trainingGroup);
        restTrainingGroupMockMvc.perform(post("/api/training-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainingGroup in the database
        List<TrainingGroup> trainingGroupList = trainingGroupRepository.findAll();
        assertThat(trainingGroupList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingGroup testTrainingGroup = trainingGroupList.get(trainingGroupList.size() - 1);
        assertThat(testTrainingGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTrainingGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingGroupRepository.findAll().size();

        // Create the TrainingGroup with an existing ID
        trainingGroup.setId(1L);
        TrainingGroupDTO trainingGroupDTO = trainingGroupMapper.toDto(trainingGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingGroupMockMvc.perform(post("/api/training-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingGroup in the database
        List<TrainingGroup> trainingGroupList = trainingGroupRepository.findAll();
        assertThat(trainingGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrainingGroups() throws Exception {
        // Initialize the database
        trainingGroupRepository.saveAndFlush(trainingGroup);

        // Get all the trainingGroupList
        restTrainingGroupMockMvc.perform(get("/api/training-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTrainingGroup() throws Exception {
        // Initialize the database
        trainingGroupRepository.saveAndFlush(trainingGroup);

        // Get the trainingGroup
        restTrainingGroupMockMvc.perform(get("/api/training-groups/{id}", trainingGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trainingGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingGroup() throws Exception {
        // Get the trainingGroup
        restTrainingGroupMockMvc.perform(get("/api/training-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingGroup() throws Exception {
        // Initialize the database
        trainingGroupRepository.saveAndFlush(trainingGroup);
        int databaseSizeBeforeUpdate = trainingGroupRepository.findAll().size();

        // Update the trainingGroup
        TrainingGroup updatedTrainingGroup = trainingGroupRepository.findOne(trainingGroup.getId());
        // Disconnect from session so that the updates on updatedTrainingGroup are not directly saved in db
        em.detach(updatedTrainingGroup);
        updatedTrainingGroup
            .name(UPDATED_NAME);
        TrainingGroupDTO trainingGroupDTO = trainingGroupMapper.toDto(updatedTrainingGroup);

        restTrainingGroupMockMvc.perform(put("/api/training-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingGroupDTO)))
            .andExpect(status().isOk());

        // Validate the TrainingGroup in the database
        List<TrainingGroup> trainingGroupList = trainingGroupRepository.findAll();
        assertThat(trainingGroupList).hasSize(databaseSizeBeforeUpdate);
        TrainingGroup testTrainingGroup = trainingGroupList.get(trainingGroupList.size() - 1);
        assertThat(testTrainingGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingGroup() throws Exception {
        int databaseSizeBeforeUpdate = trainingGroupRepository.findAll().size();

        // Create the TrainingGroup
        TrainingGroupDTO trainingGroupDTO = trainingGroupMapper.toDto(trainingGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrainingGroupMockMvc.perform(put("/api/training-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainingGroup in the database
        List<TrainingGroup> trainingGroupList = trainingGroupRepository.findAll();
        assertThat(trainingGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrainingGroup() throws Exception {
        // Initialize the database
        trainingGroupRepository.saveAndFlush(trainingGroup);
        int databaseSizeBeforeDelete = trainingGroupRepository.findAll().size();

        // Get the trainingGroup
        restTrainingGroupMockMvc.perform(delete("/api/training-groups/{id}", trainingGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingGroup> trainingGroupList = trainingGroupRepository.findAll();
        assertThat(trainingGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingGroup.class);
        TrainingGroup trainingGroup1 = new TrainingGroup();
        trainingGroup1.setId(1L);
        TrainingGroup trainingGroup2 = new TrainingGroup();
        trainingGroup2.setId(trainingGroup1.getId());
        assertThat(trainingGroup1).isEqualTo(trainingGroup2);
        trainingGroup2.setId(2L);
        assertThat(trainingGroup1).isNotEqualTo(trainingGroup2);
        trainingGroup1.setId(null);
        assertThat(trainingGroup1).isNotEqualTo(trainingGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingGroupDTO.class);
        TrainingGroupDTO trainingGroupDTO1 = new TrainingGroupDTO();
        trainingGroupDTO1.setId(1L);
        TrainingGroupDTO trainingGroupDTO2 = new TrainingGroupDTO();
        assertThat(trainingGroupDTO1).isNotEqualTo(trainingGroupDTO2);
        trainingGroupDTO2.setId(trainingGroupDTO1.getId());
        assertThat(trainingGroupDTO1).isEqualTo(trainingGroupDTO2);
        trainingGroupDTO2.setId(2L);
        assertThat(trainingGroupDTO1).isNotEqualTo(trainingGroupDTO2);
        trainingGroupDTO1.setId(null);
        assertThat(trainingGroupDTO1).isNotEqualTo(trainingGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trainingGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trainingGroupMapper.fromId(null)).isNull();
    }
}
