package dto;

import javax.security.enterprise.credential.Password;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {

    private Long id;
    private String name;
    private String surname;


    @NotNull(message = "Login cannot be empty")
    @NotEmpty(message = "Login cannot be empty")
    @NotBlank(message = "Login cannot be blank")
    private String email;


    @NotNull(message = "Login cannot be empty")
    @NotEmpty(message = "Login cannot be empty")
    @NotBlank(message = "Login cannot be blank")
    private Password password;
    private String role;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
