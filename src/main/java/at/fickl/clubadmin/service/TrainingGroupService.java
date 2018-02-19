package at.fickl.clubadmin.service;

import at.fickl.clubadmin.service.dto.TrainingGroupDTO;
import java.util.List;

/**
 * Service Interface for managing TrainingGroup.
 */
public interface TrainingGroupService {

    /**
     * Save a trainingGroup.
     *
     * @param trainingGroupDTO the entity to save
     * @return the persisted entity
     */
    TrainingGroupDTO save(TrainingGroupDTO trainingGroupDTO);

    /**
     * Get all the trainingGroups.
     *
     * @return the list of entities
     */
    List<TrainingGroupDTO> findAll();

    /**
     * Get the "id" trainingGroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TrainingGroupDTO findOne(Long id);

    /**
     * Delete the "id" trainingGroup.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
