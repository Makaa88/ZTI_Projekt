package project.expenses.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.expenses.models.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
}
