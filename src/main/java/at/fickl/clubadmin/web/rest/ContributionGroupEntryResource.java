package at.fickl.clubadmin.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.fickl.clubadmin.service.ContributionGroupEntryService;
import at.fickl.clubadmin.web.rest.errors.BadRequestAlertException;
import at.fickl.clubadmin.web.rest.util.HeaderUtil;
import at.fickl.clubadmin.service.dto.ContributionGroupEntryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ContributionGroupEntry.
 */
@RestController
@RequestMapping("/api")
public class ContributionGroupEntryResource {

    private final Logger log = LoggerFactory.getLogger(ContributionGroupEntryResource.class);

    private static final String ENTITY_NAME = "contributionGroupEntry";

    private final ContributionGroupEntryService contributionGroupEntryService;

    public ContributionGroupEntryResource(ContributionGroupEntryService contributionGroupEntryService) {
        this.contributionGroupEntryService = contributionGroupEntryService;
    }

    /**
     * POST  /contribution-group-entries : Create a new contributionGroupEntry.
     *
     * @param contributionGroupEntryDTO the contributionGroupEntryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contributionGroupEntryDTO, or with status 400 (Bad Request) if the contributionGroupEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contribution-group-entries")
    @Timed
    public ResponseEntity<ContributionGroupEntryDTO> createContributionGroupEntry(@RequestBody ContributionGroupEntryDTO contributionGroupEntryDTO) throws URISyntaxException {
        log.debug("REST request to save ContributionGroupEntry : {}", contributionGroupEntryDTO);
        if (contributionGroupEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new contributionGroupEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContributionGroupEntryDTO result = contributionGroupEntryService.save(contributionGroupEntryDTO);
        return ResponseEntity.created(new URI("/api/contribution-group-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contribution-group-entries : Updates an existing contributionGroupEntry.
     *
     * @param contributionGroupEntryDTO the contributionGroupEntryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contributionGroupEntryDTO,
     * or with status 400 (Bad Request) if the contributionGroupEntryDTO is not valid,
     * or with status 500 (Internal Server Error) if the contributionGroupEntryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contribution-group-entries")
    @Timed
    public ResponseEntity<ContributionGroupEntryDTO> updateContributionGroupEntry(@RequestBody ContributionGroupEntryDTO contributionGroupEntryDTO) throws URISyntaxException {
        log.debug("REST request to update ContributionGroupEntry : {}", contributionGroupEntryDTO);
        if (contributionGroupEntryDTO.getId() == null) {
            return createContributionGroupEntry(contributionGroupEntryDTO);
        }
        ContributionGroupEntryDTO result = contributionGroupEntryService.save(contributionGroupEntryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contributionGroupEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contribution-group-entries : get all the contributionGroupEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contributionGroupEntries in body
     */
    @GetMapping("/contribution-group-entries")
    @Timed
    public List<ContributionGroupEntryDTO> getAllContributionGroupEntries() {
        log.debug("REST request to get all ContributionGroupEntries");
        return contributionGroupEntryService.findAll();
        }

    /**
     * GET  /contribution-group-entries/:id : get the "id" contributionGroupEntry.
     *
     * @param id the id of the contributionGroupEntryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contributionGroupEntryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contribution-group-entries/{id}")
    @Timed
    public ResponseEntity<ContributionGroupEntryDTO> getContributionGroupEntry(@PathVariable Long id) {
        log.debug("REST request to get ContributionGroupEntry : {}", id);
        ContributionGroupEntryDTO contributionGroupEntryDTO = contributionGroupEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contributionGroupEntryDTO));
    }

    /**
     * DELETE  /contribution-group-entries/:id : delete the "id" contributionGroupEntry.
     *
     * @param id the id of the contributionGroupEntryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contribution-group-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteContributionGroupEntry(@PathVariable Long id) {
        log.debug("REST request to delete ContributionGroupEntry : {}", id);
        contributionGroupEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
