package project.expenses.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.expenses.models.Calendar;
import project.expenses.models.Expenses;
import project.expenses.models.Person;
import project.expenses.repositiories.ExpensesRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class ExpensesController {

    @Autowired
    private ExpensesRepository expensesRepository;

    @GetMapping("/addExpense")
    public String addExpense(Model model)
    {
        Expenses expenses = new Expenses();
        Calendar calendar = new Calendar();
        model.addAttribute("expenseToAdd", expenses);
        model.addAttribute("calendar", calendar);
        return "addExpense";
    }

    @PostMapping("/addExpense")
    public String saveExpense(@Valid Expenses expenses, @Valid Calendar calendar, Model model, HttpSession session)
    {
        Person person = (Person)session.getAttribute("person");
        expenses.setPerson(person);
        expenses.setDate(calendar.getDate());

        try
        {
            expensesRepository.save(expenses);
        }
        catch(DataIntegrityViolationException e)
        {
            //TODO set proper error in html page
            return "error";
        }

        return "expenses";
    }

}
