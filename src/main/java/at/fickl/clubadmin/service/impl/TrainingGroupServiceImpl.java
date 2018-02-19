package at.fickl.clubadmin.service.impl;

import at.fickl.clubadmin.service.TrainingGroupService;
import at.fickl.clubadmin.domain.TrainingGroup;
import at.fickl.clubadmin.repository.TrainingGroupRepository;
import at.fickl.clubadmin.service.dto.TrainingGroupDTO;
import at.fickl.clubadmin.service.mapper.TrainingGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TrainingGroup.
 */
@Service
@Transactional
public class TrainingGroupServiceImpl implements TrainingGroupService {

    private final Logger log = LoggerFactory.getLogger(TrainingGroupServiceImpl.class);

    private final TrainingGroupRepository trainingGroupRepository;

    private final TrainingGroupMapper trainingGroupMapper;

    public TrainingGroupServiceImpl(TrainingGroupRepository trainingGroupRepository, TrainingGroupMapper trainingGroupMapper) {
        this.trainingGroupRepository = trainingGroupRepository;
        this.trainingGroupMapper = trainingGroupMapper;
    }

    /**
     * Save a trainingGroup.
     *
     * @param trainingGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TrainingGroupDTO save(TrainingGroupDTO trainingGroupDTO) {
        log.debug("Request to save TrainingGroup : {}", trainingGroupDTO);
        TrainingGroup trainingGroup = trainingGroupMapper.toEntity(trainingGroupDTO);
        trainingGroup = trainingGroupRepository.save(trainingGroup);
        return trainingGroupMapper.toDto(trainingGroup);
    }

    /**
     * Get all the trainingGroups.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrainingGroupDTO> findAll() {
        log.debug("Request to get all TrainingGroups");
        return trainingGroupRepository.findAll().stream()
            .map(trainingGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one trainingGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TrainingGroupDTO findOne(Long id) {
        log.debug("Request to get TrainingGroup : {}", id);
        TrainingGroup trainingGroup = trainingGroupRepository.findOne(id);
        return trainingGroupMapper.toDto(trainingGroup);
    }

    /**
     * Delete the trainingGroup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrainingGroup : {}", id);
        trainingGroupRepository.delete(id);
    }
}
