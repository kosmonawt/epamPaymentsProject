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

    public boolean existsByEmail(String email) {
        return userDAO.exist(email);
    }


    public void save(UserDTO userDTO) {
        userDAO.create(userDTO);
    }

    public UserDTO getUserByEmail(String email) {
        return userDAO.getByName(email);
    }

    public boolean find(String email, String password) {
        UserDTO userDTO = getUserByEmail(email);
        return userDTO.getPassword().equals(password);
    }
}
