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
import project.expenses.service.ExpensesService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    private ExpensesService expensesService;

    @PostMapping("/addExpense")
    public ExpensesDto addExpense(@RequestBody ExpensesDto expensesDto)
    {

        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(expensesService.addExpense(expensesDto));

        expensesDto.setResponseStatus(responseStatus);

        return expensesDto;
    }

    private ExpensesDto performEditExpense(ExpensesDto expensesDto, long id)
    {

        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(expensesService.editExpense(expensesDto, id));
        expensesDto.setResponseStatus(responseStatus);
        return expensesDto;
    }


    @PutMapping("/edit/{id}")
    public ExpensesDto editExpense(@RequestBody ExpensesDto expensesDto, @PathVariable long id)
    {
        return performEditExpense(expensesDto, id);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseStatus deleteExpense(@PathVariable long id)
    {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(expensesService.deleteExpense(id));
        return responseStatus;
    }

    @GetMapping("/getExpenses/{id}")
    public Iterable<ExpensesDto> getExpenses(@PathVariable long id)
    {
        List<Expenses>expensesList = expensesService.getAll(id);
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
        List<Expenses> expensesList = expensesService.getUserSortedExpenses(expensesDto);
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
