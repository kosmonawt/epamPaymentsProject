package entity;

import javax.security.enterprise.credential.Password;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private long id;
    private String login;
    private Password password;
    private Role role;

    public User(String email, Password password) {
        this.login = email;
        this.password = password;
        this.role = Role.USER;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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


    public boolean isAdmin() {
        return this.role.equals(Role.ADMIN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getLogin().equals(user.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id= ").append(id);
        sb.append(", login= '").append(login).append('\'');
        sb.append(", role= ").append(role);
        sb.append('}');
        return sb.toString();
    }
}
