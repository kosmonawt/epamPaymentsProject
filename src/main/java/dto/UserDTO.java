package dto;

import entity.Role;
import entity.Status;

public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
    private String status;

    public UserDTO() {
    }

    public boolean isAdmin() {
        return role.equalsIgnoreCase(Role.ADMIN.name());
    }

    public boolean isBlocked() {
        return status.equalsIgnoreCase(Status.BLOCKED.name());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
