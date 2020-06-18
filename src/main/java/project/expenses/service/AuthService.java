package project.expenses.service;

import project.expenses.models.Person;
import project.expenses.models.dto.PersonDto;

import java.util.Optional;

public interface AuthService {

    Person createAccount(PersonDto person);
    Optional<Person> loginUser(PersonDto personDto);

}
