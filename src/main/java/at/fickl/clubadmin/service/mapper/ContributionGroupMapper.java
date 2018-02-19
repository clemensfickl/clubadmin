package at.fickl.clubadmin.service.mapper;

import at.fickl.clubadmin.domain.*;
import at.fickl.clubadmin.service.dto.ContributionGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContributionGroup and its DTO ContributionGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContributionGroupMapper extends EntityMapper<ContributionGroupDTO, ContributionGroup> {



    default ContributionGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContributionGroup contributionGroup = new ContributionGroup();
        contributionGroup.setId(id);
        return contributionGroup;
    }
}
