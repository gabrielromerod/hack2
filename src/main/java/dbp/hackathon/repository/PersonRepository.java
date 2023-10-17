package dbp.hackathon.repository;

import dbp.hackathon.model.Group;
import dbp.hackathon.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
