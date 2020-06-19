package project.expenses.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.expenses.models.LogInDataModel;
import project.expenses.models.Person;
import project.expenses.models.dto.PersonDto;
import project.expenses.repositiories.PersonRepository;
import project.expenses.service.AuthService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private AuthService authService;

    @PostMapping("/createAccount")
    public Person saveAccount(@RequestBody PersonDto person, Model model)
    {
        System.out.println("Received create account request");

        return authService.createAccount(person);
    }

    @PostMapping("/login")
    public LogInDataModel loginUser(@RequestBody PersonDto person, HttpSession session)
    {
        System.out.println("Received login request");
        LogInDataModel logInDataModel =  new LogInDataModel(false, null);

        Optional<Person> loginPerson = authService.loginUser(person);
        if(loginPerson.isPresent()) {
            System.out.println("Login successfull");
            session.setAttribute("person", loginPerson.get());
            logInDataModel.setPerson(loginPerson.get());
            logInDataModel.setLoginStatus(true);

            return logInDataModel;
        }

        return logInDataModel;
    }

    @PostMapping("/logout")
    public LogInDataModel logOut(HttpSession session)
    {
        session.removeAttribute("person");
        return new LogInDataModel(true, null);
    }



}
