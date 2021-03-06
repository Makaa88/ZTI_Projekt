package project.expenses.repositiories;

import project.expenses.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsernameAndPassword(String username, String password);
    Optional<Person> findById(Long id);
}
