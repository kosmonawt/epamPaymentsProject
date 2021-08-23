package controller.model;

import javax.security.enterprise.credential.Password;

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Password password;
    private Role role;

    public User(String firstName, String lastName, String email, Password password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id= ").append(id);
        sb.append(", firstName= '").append(firstName).append('\'');
        sb.append(", lastName= '").append(lastName).append('\'');
        sb.append(", email= '").append(email).append('\'');
        sb.append(", password= '").append(password).append('\'');
        sb.append(", role= ").append(role);
        sb.append('}');
        return sb.toString();
    }
}
