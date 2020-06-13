package project.expenses.models;

public class LogInDataModel {
    private Boolean loginStatus;
    private Person person;

    public LogInDataModel(){}

    public LogInDataModel(Boolean loginStatus, Person person) {
        this.loginStatus = loginStatus;
        this.person = person;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
