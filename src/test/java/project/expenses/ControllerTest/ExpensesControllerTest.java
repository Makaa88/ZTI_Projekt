package project.expenses.ControllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import project.expenses.controllers.ExpensesController;
import project.expenses.models.dto.ExpensesDto;
import project.expenses.models.dto.PersonDto;
import project.expenses.service.ExpensesService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpensesController.class)
public class ExpensesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpensesService expensesService;

    @Test
    public void addExpenseSuccessTest() throws Exception
    {
        ExpensesDto expensesDto = new ExpensesDto();
        expensesDto.setGoal("test");
        expensesDto.setAmount(100.0);
        when(expensesService.addExpense(any(ExpensesDto.class))).thenReturn(true);

        mockMvc.perform(post("/expenses/addExpense")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expensesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value(expensesDto.getGoal()))
                .andExpect(jsonPath("$.amount").value(expensesDto.getAmount()))
                .andExpect(jsonPath("$.responseStatus.successResponse").value(true));
    }

    @Test
    public void addExpenseFalseTest() throws Exception
    {
        ExpensesDto expensesDto = new ExpensesDto();
        expensesDto.setGoal("test");
        expensesDto.setAmount(100.0);
        when(expensesService.addExpense(any(ExpensesDto.class))).thenReturn(false);

        mockMvc.perform(post("/expenses/addExpense")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expensesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value(expensesDto.getGoal()))
                .andExpect(jsonPath("$.amount").value(expensesDto.getAmount()))
                .andExpect(jsonPath("$.responseStatus.successResponse").value(false));
    }


    @Test
    public void editExpenseFalseTest() throws Exception
    {
        ExpensesDto expensesDto = new ExpensesDto();
        expensesDto.setGoal("test");
        expensesDto.setAmount(100.0);
        when(expensesService.editExpense(any(ExpensesDto.class), any(long.class))).thenReturn(false);

        mockMvc.perform(put("/expenses/edit/1")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expensesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value(expensesDto.getGoal()))
                .andExpect(jsonPath("$.amount").value(expensesDto.getAmount()))
                .andExpect(jsonPath("$.responseStatus.successResponse").value(false));
    }

    @Test
    public void editExpenseSuccessTest() throws Exception
    {
        ExpensesDto expensesDto = new ExpensesDto();
        expensesDto.setGoal("test");
        expensesDto.setAmount(100.0);
        when(expensesService.editExpense(any(ExpensesDto.class), any(long.class))).thenReturn(true);

        mockMvc.perform(put("/expenses/edit/1")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expensesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value(expensesDto.getGoal()))
                .andExpect(jsonPath("$.amount").value(expensesDto.getAmount()))
                .andExpect(jsonPath("$.responseStatus.successResponse").value(true));
    }

    @Test
    public void deleteExpenseSuccessTest() throws Exception
    {
        ExpensesDto expensesDto = new ExpensesDto();
        expensesDto.setGoal("test");
        expensesDto.setAmount(100.0);
        when(expensesService.deleteExpense(any(long.class))).thenReturn(true);

        mockMvc.perform(delete("/expenses/delete/1")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expensesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successResponse").value(true));
    }

    @Test
    public void deleteExpenseFalseTest() throws Exception
    {
        ExpensesDto expensesDto = new ExpensesDto();
        expensesDto.setGoal("test");
        expensesDto.setAmount(100.0);
        when(expensesService.deleteExpense(any(long.class))).thenReturn(false);

        mockMvc.perform(delete("/expenses/delete/1")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expensesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successResponse").value(false));
    }



}
