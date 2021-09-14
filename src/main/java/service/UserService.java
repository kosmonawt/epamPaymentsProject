package service;

import dao.DAO;
import dao.impl.UserDaoImpl;
import dto.UserDTO;


public class UserService {

    private static UserService instance;
    private final DAO<UserDTO> userDAO = new UserDaoImpl();


    public static synchronized UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }
        return instance;

    }

    public boolean existsByEmail(String email) {
        return userDAO.exist(email);
    }


    public void save(UserDTO userDTO) {
        userDAO.create(userDTO);
    }

    public UserDTO getUserByEmail(String email) {
        try {
            return userDAO.getByName(email);
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
}
