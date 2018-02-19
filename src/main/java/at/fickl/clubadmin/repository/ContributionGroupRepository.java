package at.fickl.clubadmin.repository;

import at.fickl.clubadmin.domain.ContributionGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContributionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContributionGroupRepository extends JpaRepository<ContributionGroup, Long> {

}
