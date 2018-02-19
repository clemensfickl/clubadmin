package at.fickl.clubadmin.service;

import at.fickl.clubadmin.service.dto.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Member.
 */
public interface MemberService {

    /**
     * Save a member.
     *
     * @param memberDTO the entity to save
     * @return the persisted entity
     */
    MemberDTO save(MemberDTO memberDTO);

    /**
     * Get all the members.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MemberDTO> findAll(Pageable pageable);

    /**
     * Get the "id" member.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MemberDTO findOne(Long id);

    /**
     * Delete the "id" member.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
