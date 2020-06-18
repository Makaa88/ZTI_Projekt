package project.expenses.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import project.expenses.controllers.IncomeController;
import project.expenses.models.dto.ExpensesDto;
import project.expenses.models.dto.IncomeDto;
import project.expenses.service.IncomeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IncomeController.class)
public class IncomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncomeService incomeService;


    @Test
    public void addIncomeSuccessTest() throws Exception
    {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setGoal("test");
        incomeDto.setAmount(100.0);
        when(incomeService.addIncome(any(IncomeDto.class))).thenReturn(true);

        mockMvc.perform(post("/income/addIncome")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(incomeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value(incomeDto.getGoal()))
                .andExpect(jsonPath("$.amount").value(incomeDto.getAmount()))
                .andExpect(jsonPath("$.responseStatus.successResponse").value(true));
    }

    @Test
    public void addIncomeFalseTest() throws Exception
    {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setGoal("test");
        incomeDto.setAmount(100.0);
        when(incomeService.addIncome(any(IncomeDto.class))).thenReturn(false);

        mockMvc.perform(post("/income/addIncome")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(incomeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value(incomeDto.getGoal()))
                .andExpect(jsonPath("$.amount").value(incomeDto.getAmount()))
                .andExpect(jsonPath("$.responseStatus.successResponse").value(false));
    }


    @Test
    public void editIncomeFalseTest() throws Exception
    {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setGoal("test");
        incomeDto.setAmount(100.0);
        when(incomeService.editIncome(any(IncomeDto.class), any(long.class))).thenReturn(false);

        mockMvc.perform(put("/income/edit/1")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(incomeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value(incomeDto.getGoal()))
                .andExpect(jsonPath("$.amount").value(incomeDto.getAmount()))
                .andExpect(jsonPath("$.responseStatus.successResponse").value(false));
    }

    @Test
    public void editIncomeSuccessTest() throws Exception
    {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setGoal("test");
        incomeDto.setAmount(100.0);
        when(incomeService.editIncome(any(IncomeDto.class), any(long.class))).thenReturn(true);

        mockMvc.perform(put("/income/edit/1")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(incomeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value(incomeDto.getGoal()))
                .andExpect(jsonPath("$.amount").value(incomeDto.getAmount()))
                .andExpect(jsonPath("$.responseStatus.successResponse").value(true));
    }

    @Test
    public void deleteIncomeSuccessTest() throws Exception
    {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setGoal("test");
        incomeDto.setAmount(100.0);
        when(incomeService.deleteIncome(any(long.class))).thenReturn(true);

        mockMvc.perform(delete("/income/delete/1")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(incomeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successResponse").value(true));
    }

    @Test
    public void deleteIncomeFalseTest() throws Exception
    {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setGoal("test");
        incomeDto.setAmount(100.0);
        when(incomeService.deleteIncome(any(long.class))).thenReturn(false);

        mockMvc.perform(delete("/income/delete/1")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(incomeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successResponse").value(false));
    }

}
