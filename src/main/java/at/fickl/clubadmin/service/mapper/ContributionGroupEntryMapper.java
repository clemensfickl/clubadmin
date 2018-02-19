package at.fickl.clubadmin.service.mapper;

import at.fickl.clubadmin.domain.*;
import at.fickl.clubadmin.service.dto.ContributionGroupEntryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContributionGroupEntry and its DTO ContributionGroupEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {ContributionGroupMapper.class})
public interface ContributionGroupEntryMapper extends EntityMapper<ContributionGroupEntryDTO, ContributionGroupEntry> {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    ContributionGroupEntryDTO toDto(ContributionGroupEntry contributionGroupEntry);

    @Mapping(source = "groupId", target = "group")
    ContributionGroupEntry toEntity(ContributionGroupEntryDTO contributionGroupEntryDTO);

    default ContributionGroupEntry fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContributionGroupEntry contributionGroupEntry = new ContributionGroupEntry();
        contributionGroupEntry.setId(id);
        return contributionGroupEntry;
    }
}
