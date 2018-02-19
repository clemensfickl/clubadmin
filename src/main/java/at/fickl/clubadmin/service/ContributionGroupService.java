package at.fickl.clubadmin.service;

import at.fickl.clubadmin.service.dto.ContributionGroupDTO;
import java.util.List;

/**
 * Service Interface for managing ContributionGroup.
 */
public interface ContributionGroupService {

    /**
     * Save a contributionGroup.
     *
     * @param contributionGroupDTO the entity to save
     * @return the persisted entity
     */
    ContributionGroupDTO save(ContributionGroupDTO contributionGroupDTO);

    /**
     * Get all the contributionGroups.
     *
     * @return the list of entities
     */
    List<ContributionGroupDTO> findAll();

    /**
     * Get the "id" contributionGroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ContributionGroupDTO findOne(Long id);

    /**
     * Delete the "id" contributionGroup.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
