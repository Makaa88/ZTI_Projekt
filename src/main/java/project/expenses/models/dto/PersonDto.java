package project.expenses.models.dto;

public class PersonDto {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}

    public String getUsername() {return this.username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return this.password;}
    public void setPassword(String password) {this.password = password;}
}
