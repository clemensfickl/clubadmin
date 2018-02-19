package at.fickl.clubadmin.service.mapper;

import at.fickl.clubadmin.domain.*;
import at.fickl.clubadmin.service.dto.TrainingGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TrainingGroup and its DTO TrainingGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrainingGroupMapper extends EntityMapper<TrainingGroupDTO, TrainingGroup> {



    default TrainingGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrainingGroup trainingGroup = new TrainingGroup();
        trainingGroup.setId(id);
        return trainingGroup;
    }
}
