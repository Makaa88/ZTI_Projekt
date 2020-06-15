package project.expenses.controllers;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.expenses.models.*;
import project.expenses.models.ResponseStatus;
import project.expenses.models.dto.ExpensesDto;
import project.expenses.models.dto.IncomeDto;
import project.expenses.repositiories.ExpensesRepository;
import project.expenses.repositiories.IncomeRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;

    @PostMapping("/addIncome")
    public IncomeDto addExpense(@RequestBody IncomeDto incomeDto , Model model, HttpSession session)
    {
        Person person = (Person)session.getAttribute("person");

        Income income = new Income();
        income.setPerson(person);
        income.setDate(incomeDto.getDate());
        income.setAmmount(incomeDto.getAmount());
        income.setGoal(incomeDto.getGoal());

        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(true);
        try
        {
            incomeRepository.save(income);
        }
        catch(DataIntegrityViolationException e)
        {
            responseStatus.setSuccessResponse(false);
            responseStatus.setError("Error during saving expense");
            incomeDto.setResponseStatus(responseStatus);
            return incomeDto;
        }

        incomeDto.setId(person.getId());
        incomeDto.setResponseStatus(responseStatus);

        return incomeDto;
    }


    @PutMapping("/edit/{id}")
    public IncomeDto editExpense(@RequestBody IncomeDto incomeDto, @PathVariable long id, HttpSession session)
    {
        Optional<Income> optionalExpenses = incomeRepository.findById(id);
        ResponseStatus responseStatus = new ResponseStatus();

        responseStatus.setSuccessResponse(false);
        responseStatus.setError("Cannot find given expense");

        if(optionalExpenses.isPresent())
        {
            Income income = optionalExpenses.get();
            income.setGoal(incomeDto.getGoal());
            income.setAmmount(incomeDto.getAmount());
            income.setDate(incomeDto.getDate());
            income.setPerson((Person)session.getAttribute("person"));

            try
            {
                incomeRepository.save(income);
                responseStatus.setSuccessResponse(true);
                responseStatus.setError("");
            }
            catch (DataIntegrityViolationException e)
            {
                responseStatus.setError("Cannot update expense");
            }
        }

        incomeDto.setResponseStatus(responseStatus);
        return incomeDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseStatus deleteExpense(@PathVariable long id)
    {
        incomeRepository.deleteById(id);
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(true);
        return responseStatus;
    }

    @GetMapping("/getIncome")
    public Iterable<IncomeDto> getExpenses()
    {
        List<Income> incomeList = incomeRepository.findAll();
        List<IncomeDto> incomeDtoList = new ArrayList<>();

        for(Income income : incomeList)
        {
            IncomeDto incomeDto = new IncomeDto();
            incomeDto.setId(income.getId());
            incomeDto.setAmount(income.getAmmount());
            incomeDto.setDate(income.getDate());
            incomeDto.setGoal(income.getGoal());
            incomeDtoList.add(incomeDto);
        }

        return incomeDtoList;
    }

}
