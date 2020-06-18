package project.expenses.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.expenses.models.Person;
import project.expenses.models.dto.PersonDto;
import project.expenses.repositiories.PersonRepository;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person createAccount(PersonDto person)
    {
        Person personToSave = new Person();
        personToSave.setName(person.getName());
        personToSave.setSurname(person.getSurname());
        personToSave.setUsername(person.getUsername());
        personToSave.setPassword(person.getPassword());

        personRepository.save(personToSave);
        return  personToSave;
    }

    @Override
    public Optional<Person> loginUser(PersonDto personDto)
    {
        return personRepository.findByUsernameAndPassword(personDto.getUsername(), personDto.getPassword());
    }
}
