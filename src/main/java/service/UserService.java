package service;

import dao.UserDAO;
import dao.impl.UserDaoImpl;
import dto.UserDTO;

import java.util.List;


public class UserService {

    private static UserService instance;
    private final UserDAO<UserDTO> userDAO = new UserDaoImpl();


    public static synchronized UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }
        return instance;

    }

    public boolean existsByEmail(String email) {
        return userDAO.existByEmail(email);
    }


    public void save(UserDTO userDTO) {
        userDAO.create(userDTO);
    }

    public UserDTO getUserByEmail(String email) {
        try {
            return userDAO.getByEmail(email);
        } catch (NullPointerException e) {
            return new UserDTO();
        }
    }

    public boolean find(String email, String password) {
        UserDTO userDTO;
        try {
            userDTO = getUserByEmail(email);
            return userDTO.getPassword().equals(password);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public UserDTO getUserById(Long userId) {
        return userDAO.getById(userId);
    }

    public List<UserDTO> getAll() {
        return userDAO.getAll();
    }
}
