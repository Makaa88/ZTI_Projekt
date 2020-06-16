package project.expenses.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.expenses.models.Expenses;
import project.expenses.models.Person;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
    List<Expenses> findAllByPersonId(Long personId);

    @Query(value = "SELECT e FROM expenses e WHERE e.id_person=:id and MONTH(e.date)=:m and YEAR(e.date)=:y", nativeQuery = true)
    List<Expenses> getByPersonIdAndMonthAndYear(@Param("id") int id, @Param("m")int month, @Param("y")int year);
}
