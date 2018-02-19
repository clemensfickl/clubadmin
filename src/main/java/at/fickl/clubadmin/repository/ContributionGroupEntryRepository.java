package at.fickl.clubadmin.repository;

import at.fickl.clubadmin.domain.ContributionGroupEntry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContributionGroupEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContributionGroupEntryRepository extends JpaRepository<ContributionGroupEntry, Long> {

}
