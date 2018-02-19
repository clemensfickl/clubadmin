package at.fickl.clubadmin.web.rest;

import at.fickl.clubadmin.ClubadminApp;

import at.fickl.clubadmin.domain.ContributionGroup;
import at.fickl.clubadmin.repository.ContributionGroupRepository;
import at.fickl.clubadmin.service.ContributionGroupService;
import at.fickl.clubadmin.service.dto.ContributionGroupDTO;
import at.fickl.clubadmin.service.mapper.ContributionGroupMapper;
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
 * Test class for the ContributionGroupResource REST controller.
 *
 * @see ContributionGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClubadminApp.class)
public class ContributionGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ContributionGroupRepository contributionGroupRepository;

    @Autowired
    private ContributionGroupMapper contributionGroupMapper;

    @Autowired
    private ContributionGroupService contributionGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContributionGroupMockMvc;

    private ContributionGroup contributionGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContributionGroupResource contributionGroupResource = new ContributionGroupResource(contributionGroupService);
        this.restContributionGroupMockMvc = MockMvcBuilders.standaloneSetup(contributionGroupResource)
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
    public static ContributionGroup createEntity(EntityManager em) {
        ContributionGroup contributionGroup = new ContributionGroup()
            .name(DEFAULT_NAME);
        return contributionGroup;
    }

    @Before
    public void initTest() {
        contributionGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createContributionGroup() throws Exception {
        int databaseSizeBeforeCreate = contributionGroupRepository.findAll().size();

        // Create the ContributionGroup
        ContributionGroupDTO contributionGroupDTO = contributionGroupMapper.toDto(contributionGroup);
        restContributionGroupMockMvc.perform(post("/api/contribution-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ContributionGroup in the database
        List<ContributionGroup> contributionGroupList = contributionGroupRepository.findAll();
        assertThat(contributionGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ContributionGroup testContributionGroup = contributionGroupList.get(contributionGroupList.size() - 1);
        assertThat(testContributionGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createContributionGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contributionGroupRepository.findAll().size();

        // Create the ContributionGroup with an existing ID
        contributionGroup.setId(1L);
        ContributionGroupDTO contributionGroupDTO = contributionGroupMapper.toDto(contributionGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributionGroupMockMvc.perform(post("/api/contribution-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContributionGroup in the database
        List<ContributionGroup> contributionGroupList = contributionGroupRepository.findAll();
        assertThat(contributionGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContributionGroups() throws Exception {
        // Initialize the database
        contributionGroupRepository.saveAndFlush(contributionGroup);

        // Get all the contributionGroupList
        restContributionGroupMockMvc.perform(get("/api/contribution-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contributionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getContributionGroup() throws Exception {
        // Initialize the database
        contributionGroupRepository.saveAndFlush(contributionGroup);

        // Get the contributionGroup
        restContributionGroupMockMvc.perform(get("/api/contribution-groups/{id}", contributionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contributionGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContributionGroup() throws Exception {
        // Get the contributionGroup
        restContributionGroupMockMvc.perform(get("/api/contribution-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContributionGroup() throws Exception {
        // Initialize the database
        contributionGroupRepository.saveAndFlush(contributionGroup);
        int databaseSizeBeforeUpdate = contributionGroupRepository.findAll().size();

        // Update the contributionGroup
        ContributionGroup updatedContributionGroup = contributionGroupRepository.findOne(contributionGroup.getId());
        // Disconnect from session so that the updates on updatedContributionGroup are not directly saved in db
        em.detach(updatedContributionGroup);
        updatedContributionGroup
            .name(UPDATED_NAME);
        ContributionGroupDTO contributionGroupDTO = contributionGroupMapper.toDto(updatedContributionGroup);

        restContributionGroupMockMvc.perform(put("/api/contribution-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ContributionGroup in the database
        List<ContributionGroup> contributionGroupList = contributionGroupRepository.findAll();
        assertThat(contributionGroupList).hasSize(databaseSizeBeforeUpdate);
        ContributionGroup testContributionGroup = contributionGroupList.get(contributionGroupList.size() - 1);
        assertThat(testContributionGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContributionGroup() throws Exception {
        int databaseSizeBeforeUpdate = contributionGroupRepository.findAll().size();

        // Create the ContributionGroup
        ContributionGroupDTO contributionGroupDTO = contributionGroupMapper.toDto(contributionGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContributionGroupMockMvc.perform(put("/api/contribution-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ContributionGroup in the database
        List<ContributionGroup> contributionGroupList = contributionGroupRepository.findAll();
        assertThat(contributionGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContributionGroup() throws Exception {
        // Initialize the database
        contributionGroupRepository.saveAndFlush(contributionGroup);
        int databaseSizeBeforeDelete = contributionGroupRepository.findAll().size();

        // Get the contributionGroup
        restContributionGroupMockMvc.perform(delete("/api/contribution-groups/{id}", contributionGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContributionGroup> contributionGroupList = contributionGroupRepository.findAll();
        assertThat(contributionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContributionGroup.class);
        ContributionGroup contributionGroup1 = new ContributionGroup();
        contributionGroup1.setId(1L);
        ContributionGroup contributionGroup2 = new ContributionGroup();
        contributionGroup2.setId(contributionGroup1.getId());
        assertThat(contributionGroup1).isEqualTo(contributionGroup2);
        contributionGroup2.setId(2L);
        assertThat(contributionGroup1).isNotEqualTo(contributionGroup2);
        contributionGroup1.setId(null);
        assertThat(contributionGroup1).isNotEqualTo(contributionGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContributionGroupDTO.class);
        ContributionGroupDTO contributionGroupDTO1 = new ContributionGroupDTO();
        contributionGroupDTO1.setId(1L);
        ContributionGroupDTO contributionGroupDTO2 = new ContributionGroupDTO();
        assertThat(contributionGroupDTO1).isNotEqualTo(contributionGroupDTO2);
        contributionGroupDTO2.setId(contributionGroupDTO1.getId());
        assertThat(contributionGroupDTO1).isEqualTo(contributionGroupDTO2);
        contributionGroupDTO2.setId(2L);
        assertThat(contributionGroupDTO1).isNotEqualTo(contributionGroupDTO2);
        contributionGroupDTO1.setId(null);
        assertThat(contributionGroupDTO1).isNotEqualTo(contributionGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contributionGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contributionGroupMapper.fromId(null)).isNull();
    }
}
