package at.fickl.clubadmin.web.rest;

import at.fickl.clubadmin.ClubadminApp;

import at.fickl.clubadmin.domain.ContributionGroupEntry;
import at.fickl.clubadmin.repository.ContributionGroupEntryRepository;
import at.fickl.clubadmin.service.ContributionGroupEntryService;
import at.fickl.clubadmin.service.dto.ContributionGroupEntryDTO;
import at.fickl.clubadmin.service.mapper.ContributionGroupEntryMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static at.fickl.clubadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContributionGroupEntryResource REST controller.
 *
 * @see ContributionGroupEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClubadminApp.class)
public class ContributionGroupEntryResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Autowired
    private ContributionGroupEntryRepository contributionGroupEntryRepository;

    @Autowired
    private ContributionGroupEntryMapper contributionGroupEntryMapper;

    @Autowired
    private ContributionGroupEntryService contributionGroupEntryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContributionGroupEntryMockMvc;

    private ContributionGroupEntry contributionGroupEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContributionGroupEntryResource contributionGroupEntryResource = new ContributionGroupEntryResource(contributionGroupEntryService);
        this.restContributionGroupEntryMockMvc = MockMvcBuilders.standaloneSetup(contributionGroupEntryResource)
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
    public static ContributionGroupEntry createEntity(EntityManager em) {
        ContributionGroupEntry contributionGroupEntry = new ContributionGroupEntry()
            .year(DEFAULT_YEAR)
            .amount(DEFAULT_AMOUNT);
        return contributionGroupEntry;
    }

    @Before
    public void initTest() {
        contributionGroupEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createContributionGroupEntry() throws Exception {
        int databaseSizeBeforeCreate = contributionGroupEntryRepository.findAll().size();

        // Create the ContributionGroupEntry
        ContributionGroupEntryDTO contributionGroupEntryDTO = contributionGroupEntryMapper.toDto(contributionGroupEntry);
        restContributionGroupEntryMockMvc.perform(post("/api/contribution-group-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionGroupEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the ContributionGroupEntry in the database
        List<ContributionGroupEntry> contributionGroupEntryList = contributionGroupEntryRepository.findAll();
        assertThat(contributionGroupEntryList).hasSize(databaseSizeBeforeCreate + 1);
        ContributionGroupEntry testContributionGroupEntry = contributionGroupEntryList.get(contributionGroupEntryList.size() - 1);
        assertThat(testContributionGroupEntry.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testContributionGroupEntry.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createContributionGroupEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contributionGroupEntryRepository.findAll().size();

        // Create the ContributionGroupEntry with an existing ID
        contributionGroupEntry.setId(1L);
        ContributionGroupEntryDTO contributionGroupEntryDTO = contributionGroupEntryMapper.toDto(contributionGroupEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributionGroupEntryMockMvc.perform(post("/api/contribution-group-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionGroupEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContributionGroupEntry in the database
        List<ContributionGroupEntry> contributionGroupEntryList = contributionGroupEntryRepository.findAll();
        assertThat(contributionGroupEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContributionGroupEntries() throws Exception {
        // Initialize the database
        contributionGroupEntryRepository.saveAndFlush(contributionGroupEntry);

        // Get all the contributionGroupEntryList
        restContributionGroupEntryMockMvc.perform(get("/api/contribution-group-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contributionGroupEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getContributionGroupEntry() throws Exception {
        // Initialize the database
        contributionGroupEntryRepository.saveAndFlush(contributionGroupEntry);

        // Get the contributionGroupEntry
        restContributionGroupEntryMockMvc.perform(get("/api/contribution-group-entries/{id}", contributionGroupEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contributionGroupEntry.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContributionGroupEntry() throws Exception {
        // Get the contributionGroupEntry
        restContributionGroupEntryMockMvc.perform(get("/api/contribution-group-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContributionGroupEntry() throws Exception {
        // Initialize the database
        contributionGroupEntryRepository.saveAndFlush(contributionGroupEntry);
        int databaseSizeBeforeUpdate = contributionGroupEntryRepository.findAll().size();

        // Update the contributionGroupEntry
        ContributionGroupEntry updatedContributionGroupEntry = contributionGroupEntryRepository.findOne(contributionGroupEntry.getId());
        // Disconnect from session so that the updates on updatedContributionGroupEntry are not directly saved in db
        em.detach(updatedContributionGroupEntry);
        updatedContributionGroupEntry
            .year(UPDATED_YEAR)
            .amount(UPDATED_AMOUNT);
        ContributionGroupEntryDTO contributionGroupEntryDTO = contributionGroupEntryMapper.toDto(updatedContributionGroupEntry);

        restContributionGroupEntryMockMvc.perform(put("/api/contribution-group-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionGroupEntryDTO)))
            .andExpect(status().isOk());

        // Validate the ContributionGroupEntry in the database
        List<ContributionGroupEntry> contributionGroupEntryList = contributionGroupEntryRepository.findAll();
        assertThat(contributionGroupEntryList).hasSize(databaseSizeBeforeUpdate);
        ContributionGroupEntry testContributionGroupEntry = contributionGroupEntryList.get(contributionGroupEntryList.size() - 1);
        assertThat(testContributionGroupEntry.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testContributionGroupEntry.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingContributionGroupEntry() throws Exception {
        int databaseSizeBeforeUpdate = contributionGroupEntryRepository.findAll().size();

        // Create the ContributionGroupEntry
        ContributionGroupEntryDTO contributionGroupEntryDTO = contributionGroupEntryMapper.toDto(contributionGroupEntry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContributionGroupEntryMockMvc.perform(put("/api/contribution-group-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionGroupEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the ContributionGroupEntry in the database
        List<ContributionGroupEntry> contributionGroupEntryList = contributionGroupEntryRepository.findAll();
        assertThat(contributionGroupEntryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContributionGroupEntry() throws Exception {
        // Initialize the database
        contributionGroupEntryRepository.saveAndFlush(contributionGroupEntry);
        int databaseSizeBeforeDelete = contributionGroupEntryRepository.findAll().size();

        // Get the contributionGroupEntry
        restContributionGroupEntryMockMvc.perform(delete("/api/contribution-group-entries/{id}", contributionGroupEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContributionGroupEntry> contributionGroupEntryList = contributionGroupEntryRepository.findAll();
        assertThat(contributionGroupEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContributionGroupEntry.class);
        ContributionGroupEntry contributionGroupEntry1 = new ContributionGroupEntry();
        contributionGroupEntry1.setId(1L);
        ContributionGroupEntry contributionGroupEntry2 = new ContributionGroupEntry();
        contributionGroupEntry2.setId(contributionGroupEntry1.getId());
        assertThat(contributionGroupEntry1).isEqualTo(contributionGroupEntry2);
        contributionGroupEntry2.setId(2L);
        assertThat(contributionGroupEntry1).isNotEqualTo(contributionGroupEntry2);
        contributionGroupEntry1.setId(null);
        assertThat(contributionGroupEntry1).isNotEqualTo(contributionGroupEntry2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContributionGroupEntryDTO.class);
        ContributionGroupEntryDTO contributionGroupEntryDTO1 = new ContributionGroupEntryDTO();
        contributionGroupEntryDTO1.setId(1L);
        ContributionGroupEntryDTO contributionGroupEntryDTO2 = new ContributionGroupEntryDTO();
        assertThat(contributionGroupEntryDTO1).isNotEqualTo(contributionGroupEntryDTO2);
        contributionGroupEntryDTO2.setId(contributionGroupEntryDTO1.getId());
        assertThat(contributionGroupEntryDTO1).isEqualTo(contributionGroupEntryDTO2);
        contributionGroupEntryDTO2.setId(2L);
        assertThat(contributionGroupEntryDTO1).isNotEqualTo(contributionGroupEntryDTO2);
        contributionGroupEntryDTO1.setId(null);
        assertThat(contributionGroupEntryDTO1).isNotEqualTo(contributionGroupEntryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contributionGroupEntryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contributionGroupEntryMapper.fromId(null)).isNull();
    }
}
