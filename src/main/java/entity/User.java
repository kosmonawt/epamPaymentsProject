package entity;

public class User extends Entity {

    private static final long serialVersionUID = -6889036256149495388L;

    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
    private Status status;

    public User() {
        this.role = Role.USER;
        this.status = Status.ACTIVE;
    }

    public boolean isAdmin() {
        return this.role.equals(Role.ADMIN);
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
