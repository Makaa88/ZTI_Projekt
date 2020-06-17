package project.expenses.controllers;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.expenses.models.Calendar;
import project.expenses.models.Expenses;
import project.expenses.models.Person;
import project.expenses.models.ResponseStatus;
import project.expenses.models.dto.ExpensesDto;
import project.expenses.models.dto.PersonDto;
import project.expenses.repositiories.ExpensesRepository;
import project.expenses.repositiories.PersonRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/addExpense")
    public ExpensesDto addExpense(@RequestBody ExpensesDto expensesDto)
    {

        Person person = personRepository.findById(expensesDto.getPersonId()).get();
        Expenses expenses = new Expenses();
        expenses.setPerson(person);
        expenses.setDate(expensesDto.getDate());
        expenses.setAmmount(expensesDto.getAmount());
        expenses.setGoal(expensesDto.getGoal());

        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(true);
        try
        {
            expensesRepository.save(expenses);
        }
        catch(DataIntegrityViolationException e)
        {
            responseStatus.setSuccessResponse(false);
            responseStatus.setError("Error during saving expense");
            expensesDto.setResponseStatus(responseStatus);
            return expensesDto;
        }

        //expensesDto.setId(person.getId());
        expensesDto.setResponseStatus(responseStatus);

        return expensesDto;
    }

    private ExpensesDto performEditExpense(ExpensesDto expensesDto, long id)
    {
        Person person = personRepository.findById(expensesDto.getPersonId()).get();
        Optional<Expenses> optionalExpenses = expensesRepository.findById(id);
        ResponseStatus responseStatus = new ResponseStatus();

        responseStatus.setSuccessResponse(false);
        responseStatus.setError("Cannot find given expense");

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
                responseStatus.setSuccessResponse(true);
                responseStatus.setError("");
            }
            catch (DataIntegrityViolationException e)
            {
                responseStatus.setError("Cannot update expense");
            }
        }

        expensesDto.setResponseStatus(responseStatus);
        return expensesDto;
    }


    @PutMapping("/edit/{id}")
    public ExpensesDto editExpense(@RequestBody ExpensesDto expensesDto, @PathVariable long id, @SessionAttribute("person") Person person)
    {
        return performEditExpense(expensesDto, id);
    }


    @GetMapping("/edit/{id}")
    public ExpensesDto editExpenseGet(@RequestBody ExpensesDto expensesDto, @PathVariable long id, @SessionAttribute("person") Person person)
    {
        return performEditExpense(expensesDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseStatus deleteExpense(@PathVariable long id)
    {
        expensesRepository.deleteById(id);
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(true);
        return responseStatus;
    }

    @GetMapping("/getExpenses")
    public Iterable<ExpensesDto> getExpenses()
    {
         List<Expenses> expensesList = expensesRepository.findAll();
         List<ExpensesDto> expensesDtoList = new ArrayList<>();

         for(Expenses expenses : expensesList)
         {
             expensesDtoList.add(fillExpensesDto(expenses));
         }

         return expensesDtoList;
    }

    @PostMapping("/getSorted")
    public Iterable<ExpensesDto> getSortedExpenses(@RequestBody ExpensesDto expensesDto)
    {
        System.out.println(expensesDto.getPersonId());
        Person person = personRepository.findById(expensesDto.getPersonId()).get();
        List<Expenses> expensesList = expensesRepository.findAllByPersonId(person.getId());
        List<ExpensesDto> expensesDtoList = new ArrayList<>();


        for(Expenses expenses : expensesList)
        {
            if(expenses.getDate().getMonth().equals(expensesDto.getDate().getMonth()) && expenses.getDate().getYear() == expensesDto.getDate().getYear())
                expensesDtoList.add(fillExpensesDto(expenses));
        }
        return  expensesDtoList;
    }

    private ExpensesDto fillExpensesDto(Expenses expenses)
    {
        ExpensesDto expensesDto = new ExpensesDto();
        expensesDto.setId(expenses.getId());
        expensesDto.setAmount(expenses.getAmmount());
        expensesDto.setDate(expenses.getDate());
        expensesDto.setGoal(expenses.getGoal());
        return expensesDto;
    }

}



// {this.state.sorted && this.state.data.filter(e => new Date(e.date).getMonth()== new Date(constants.EXPENSES_DATE).getMonth() && new Date(e.date).getFullYear() == new Date(constants.EXPENSES_DATE).getFullYear()).map(element => <Expense expense={element}/> )}
