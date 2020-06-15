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
import project.expenses.repositiories.ExpensesRepository;

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

    @PostMapping("/addExpense")
    public ExpensesDto addExpense(@RequestBody ExpensesDto expensesDto ,Model model, HttpSession session)
    {
        Person person = (Person)session.getAttribute("person");

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


    @PutMapping("/edit/{id}")
    public ExpensesDto editExpense(@RequestBody ExpensesDto expensesDto, @PathVariable long id, HttpSession session)
    {
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
            expenses.setPerson((Person)session.getAttribute("person"));

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
             ExpensesDto expensesDto = new ExpensesDto();
             expensesDto.setId(expenses.getId());
             expensesDto.setAmount(expenses.getAmmount());
             expensesDto.setDate(expenses.getDate());
             expensesDto.setGoal(expenses.getGoal());
             expensesDtoList.add(expensesDto);
         }

         return expensesDtoList;
    }

}
