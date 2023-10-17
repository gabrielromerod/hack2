package dbp.hackathon.repository;

import dbp.hackathon.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByMembers_Id(Long personId);  // Corrected derived query method
}
