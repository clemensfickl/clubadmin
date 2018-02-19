package at.fickl.clubadmin.service.mapper;

import at.fickl.clubadmin.domain.*;
import at.fickl.clubadmin.service.dto.MemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Member and its DTO MemberDTO.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class, ContributionGroupMapper.class})
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "contributionGroup.id", target = "contributionGroupId")
    @Mapping(source = "contributionGroup.name", target = "contributionGroupName")
    MemberDTO toDto(Member member);

    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "contributionGroupId", target = "contributionGroup")
    Member toEntity(MemberDTO memberDTO);

    default Member fromId(Long id) {
        if (id == null) {
            return null;
        }
        Member member = new Member();
        member.setId(id);
        return member;
    }
}
