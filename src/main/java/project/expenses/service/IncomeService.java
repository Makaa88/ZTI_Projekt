package project.expenses.service;

import project.expenses.models.Income;
import project.expenses.models.dto.IncomeDto;
import java.util.List;

public interface IncomeService {
    Boolean addIncome(IncomeDto incomeDto);
    Boolean editIncome(IncomeDto incomeDto, long id);
    List<Income> getUserSortedIncomes(IncomeDto incomeDto);
    List<Income> getAll(long id);
    Boolean deleteIncome(long id);
}
