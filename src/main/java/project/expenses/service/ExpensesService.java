package project.expenses.service;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import project.expenses.models.Expenses;
import project.expenses.models.dto.ExpensesDto;
import java.util.List;

public interface ExpensesService {
    Boolean addExpense(ExpensesDto expensesDto);
    Boolean editExpense(ExpensesDto expensesDto, long id);
    List<Expenses> getUserSortedExpenses(ExpensesDto expensesDto);
    List<Expenses> getAll(long id);
    Boolean deleteExpense(long id);
}
