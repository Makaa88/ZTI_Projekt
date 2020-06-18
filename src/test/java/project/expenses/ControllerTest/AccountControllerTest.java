package project.expenses.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import project.expenses.controllers.AccountController;
import project.expenses.models.Person;
import project.expenses.models.dto.PersonDto;
import project.expenses.service.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;


    @Test
    public void createAccountTest() throws Exception
    {
        Person newPerson = new Person();
        newPerson.setUsername("test");
        newPerson.setPassword("test");
        newPerson.setName("test");
        newPerson.setSurname("test");
        when(authService.createAccount(any(PersonDto.class))).thenReturn(newPerson);

        mockMvc.perform(post("/auth/createAccount")
        .contentType(APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newPerson.getName()))
                .andExpect(jsonPath("$.surname").value(newPerson.getSurname()))
                .andExpect(jsonPath("$.username").value(newPerson.getUsername()))
                .andExpect(jsonPath("$.password").value(newPerson.getPassword()));

    }

}
