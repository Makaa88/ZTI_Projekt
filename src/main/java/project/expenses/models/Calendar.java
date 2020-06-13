package project.expenses.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class Calendar {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}


    public String getDayName()
    {
        String dayName = date.getDayOfWeek().name();
        switch (dayName)
        {
            case "MONDAY":
                return "Poniedziałek";
            case "TUESDAY":
                return "Wtorek";
            case "WEDNESDAY":
                return "Środa";
            case "THURSDAY":
                return "Czwartek";
            case "FRIDAY":
                return "Piątek";
            case "SATURDAY":
                return "Sobota";
        }
        return "Niedziela";
    }
}
