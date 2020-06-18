package project.expenses.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import project.expenses.models.Income;
import project.expenses.models.Person;
import project.expenses.models.dto.IncomeDto;
import project.expenses.repositiories.IncomeRepository;
import project.expenses.repositiories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private PersonRepository personRepository;

    @Override
    public Boolean addIncome(IncomeDto incomeDto)
    {
        Person person = personRepository.findById(incomeDto.getPersonId()).get();
        Income income = new Income();
        income.setPerson(person);
        income.setDate(incomeDto.getDate());
        income.setAmmount(incomeDto.getAmount());
        income.setGoal(incomeDto.getGoal());

        try
        {
            incomeRepository.save(income);
        }
        catch(DataIntegrityViolationException e)
        {
            return false;
        }

        return true;
    }

    @Override
    public Boolean editIncome(IncomeDto incomeDto, long id)
    {
        Person person = personRepository.findById(incomeDto.getPersonId()).get();
        Optional<Income> optionalIncome = incomeRepository.findById(id);

        if(optionalIncome.isPresent())
        {
            Income income = optionalIncome.get();
            income.setGoal(incomeDto.getGoal());
            income.setAmmount(incomeDto.getAmount());
            income.setDate(incomeDto.getDate());
            income.setPerson(person);

            try
            {
                incomeRepository.save(income);
            }
            catch (DataIntegrityViolationException e)
            {
                return false;
            }

        }

        return true;
    }


    @Override
    public List<Income> getUserSortedIncomes(IncomeDto incomeDto)
    {
        Person person = personRepository.findById(incomeDto.getPersonId()).get();
        return getAll(person.getId());
    }

    @Override
    public List<Income> getAll(long id)
    {
        return  incomeRepository.findAllByPersonId(id);
    }

    @Override
    public Boolean deleteIncome(long id)
    {
        try
        {
            incomeRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e)
        {
            return false;
        }

        return true;
    }
}
