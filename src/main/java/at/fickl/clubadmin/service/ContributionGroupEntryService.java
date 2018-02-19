package at.fickl.clubadmin.service;

import at.fickl.clubadmin.service.dto.ContributionGroupEntryDTO;
import java.util.List;

/**
 * Service Interface for managing ContributionGroupEntry.
 */
public interface ContributionGroupEntryService {

    /**
     * Save a contributionGroupEntry.
     *
     * @param contributionGroupEntryDTO the entity to save
     * @return the persisted entity
     */
    ContributionGroupEntryDTO save(ContributionGroupEntryDTO contributionGroupEntryDTO);

    /**
     * Get all the contributionGroupEntries.
     *
     * @return the list of entities
     */
    List<ContributionGroupEntryDTO> findAll();

    /**
     * Get the "id" contributionGroupEntry.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ContributionGroupEntryDTO findOne(Long id);

    /**
     * Delete the "id" contributionGroupEntry.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
