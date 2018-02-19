package at.fickl.clubadmin.repository;

import at.fickl.clubadmin.domain.TrainingGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TrainingGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingGroupRepository extends JpaRepository<TrainingGroup, Long> {

}
