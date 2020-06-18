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
import project.expenses.repositiories.PersonRepository;
import project.expenses.service.IncomeService;

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
    IncomeService incomeService;

    @PostMapping("/addIncome")
    public IncomeDto addIncome(@RequestBody IncomeDto incomeDto , Model model)
    {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(incomeService.addIncome(incomeDto));

        incomeDto.setResponseStatus(responseStatus);

        return incomeDto;
    }

    private IncomeDto performEditIncome(IncomeDto incomeDto, long id)
    {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(incomeService.editIncome(incomeDto, id));
        incomeDto.setResponseStatus(responseStatus);
        return incomeDto;
    }

    @PutMapping("/edit/{id}")
    public IncomeDto ediIncome(@RequestBody IncomeDto incomeDto, @PathVariable long id)
    {
        return  performEditIncome(incomeDto, id);
    }

    @GetMapping("/edit/{id}")
    public IncomeDto ediIncomeGet(@RequestBody IncomeDto incomeDto, @PathVariable long id)
    {
        return  performEditIncome(incomeDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseStatus deleteIncome(@PathVariable long id)
    {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccessResponse(incomeService.deleteIncome(id));
        return responseStatus;
    }

    @GetMapping("/getIncome/{id}")
    public Iterable<IncomeDto> geIncomes(@PathVariable long id)
    {
        List<Income> incomeList = incomeService.getAll(id);
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


    @PostMapping("/getSorted")
    public Iterable<IncomeDto> getSortedIncomes(@RequestBody IncomeDto incomeDto)
    {
        List<Income> incomeList = incomeService.getUserSortedIncomes(incomeDto);
        List<IncomeDto> incomeDtoList = new ArrayList<>();


        for(Income income : incomeList)
        {
            if(income.getDate().getMonth().equals(incomeDto.getDate().getMonth()) && income.getDate().getYear() == incomeDto.getDate().getYear())
                incomeDtoList.add(fillExpensesDto(income));
        }
        return  incomeDtoList;
    }

    private IncomeDto fillExpensesDto(Income income)
    {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setId(income.getId());
        incomeDto.setAmount(income.getAmmount());
        incomeDto.setDate(income.getDate());
        incomeDto.setGoal(income.getGoal());
        return incomeDto;
    }

}
