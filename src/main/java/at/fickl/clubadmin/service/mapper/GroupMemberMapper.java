package at.fickl.clubadmin.service.mapper;

import at.fickl.clubadmin.domain.*;
import at.fickl.clubadmin.service.dto.GroupMemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GroupMember and its DTO GroupMemberDTO.
 */
@Mapper(componentModel = "spring", uses = {TrainingGroupMapper.class, MemberMapper.class})
public interface GroupMemberMapper extends EntityMapper<GroupMemberDTO, GroupMember> {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    @Mapping(source = "member.id", target = "memberId")
    GroupMemberDTO toDto(GroupMember groupMember);

    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "memberId", target = "member")
    GroupMember toEntity(GroupMemberDTO groupMemberDTO);

    default GroupMember fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupMember groupMember = new GroupMember();
        groupMember.setId(id);
        return groupMember;
    }
}
