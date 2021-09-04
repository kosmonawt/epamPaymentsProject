package dao;

import dto.UserDTO;
import entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();
    User getUser(long id);
    void save(UserDTO userDTO);
    void updateUser(User user);
    void deleteUSer(User user);
    boolean existByLogin(String login);


}
