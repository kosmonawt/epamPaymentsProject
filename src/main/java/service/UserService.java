package service;

import dao.UserDAO;
import dao.impl.UserDaoImpl;
import dto.UserDTO;


public class UserService {

    public static UserService userService;
    private UserDAO userDAO = new UserDaoImpl();


    public static synchronized UserService getInstance() {

        if (userService == null) {
            userService = new UserService();
        }
        return userService;

    }

    public boolean existsByLogin(String login) {
        return userDAO.existByLogin(login);
    }


    public void save(UserDTO userDTO) {
        userDAO.save(userDTO);
    }
}
