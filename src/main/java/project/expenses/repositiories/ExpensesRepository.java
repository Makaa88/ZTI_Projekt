package project.expenses.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.expenses.models.Expenses;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
}
