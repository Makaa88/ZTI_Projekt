package project.expenses.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import project.expenses.models.Expenses;
import project.expenses.models.Person;
import project.expenses.models.ResponseStatus;
import project.expenses.models.dto.ExpensesDto;
import project.expenses.repositiories.ExpensesRepository;
import project.expenses.repositiories.PersonRepository;

import java.util.List;
import java.util.Optional;


@Service
public class ExpensesServiceImpl implements ExpensesService {

    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private PersonRepository personRepository;

    @Override
    public Boolean addExpense(ExpensesDto expensesDto)
    {
        Person person = personRepository.findById(expensesDto.getPersonId()).get();
        Expenses expenses = new Expenses();
        expenses.setPerson(person);
        expenses.setDate(expensesDto.getDate());
        expenses.setAmmount(expensesDto.getAmount());
        expenses.setGoal(expensesDto.getGoal());

        try
        {
            expensesRepository.save(expenses);
        }
        catch(DataIntegrityViolationException e)
        {
            return false;
        }

        return true;
    }

    @Override
    public Boolean editExpense(ExpensesDto expensesDto, long id)
    {
        Person person = personRepository.findById(expensesDto.getPersonId()).get();
        Optional<Expenses> optionalExpenses = expensesRepository.findById(id);

        if(optionalExpenses.isPresent())
        {
            Expenses expenses = optionalExpenses.get();
            expenses.setGoal(expensesDto.getGoal());
            expenses.setAmmount(expensesDto.getAmount());
            expenses.setDate(expensesDto.getDate());
            expenses.setPerson(person);

            try
            {
                expensesRepository.save(expenses);
            }
            catch (DataIntegrityViolationException e)
            {
                return false;
            }

        }

        return true;
    }


    @Override
    public List<Expenses> getUserSortedExpenses(ExpensesDto expensesDto)
    {
        Person person = personRepository.findById(expensesDto.getPersonId()).get();
        return getAll(person.getId());
    }

    @Override
    public List<Expenses> getAll(long id)
    {
        return  expensesRepository.findAllByPersonId(id);
    }

    @Override
    public Boolean deleteExpense(long id)
    {
        try
        {
            expensesRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e)
        {
            return false;
        }

        return true;
    }


}
