package service;

import dao.DAO;
import dao.impl.UserDaoImpl;
import dto.UserDTO;


public class UserService {

    public static UserService userService;
    private DAO<UserDTO> userDAO = new UserDaoImpl();


    public static synchronized UserService getInstance() {

        if (userService == null) {
            userService = new UserService();
        }
        return userService;

    }

    public boolean existsByLogin(String login) {
        return userDAO.exist(login);
    }


    public void save(UserDTO userDTO) {
        userDAO.create(userDTO);
    }
}
