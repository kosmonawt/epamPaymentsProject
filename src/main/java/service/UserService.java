package service;

import dao.UserDAO;
import dao.impl.UserDaoImpl;
import dto.UserDTO;
import entity.Status;
import org.apache.log4j.Logger;

import java.util.List;


public class UserService {
    private final Logger logger = Logger.getLogger(UserService.class);
    private static UserService instance;
    private final UserDAO<UserDTO> userDAO = new UserDaoImpl();


    public static synchronized UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }
        return instance;

    }

    public boolean existsByEmail(String email) {
        try {
            return userDAO.existByEmail(email);
        } catch (NullPointerException e) {
            return false;
        }
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


    public void changeUserStatus(String userEmail, String status) {
        logger.warn("Status before update is: " + status);
        try {
            UserDTO userDTO = userDAO.getByEmail(userEmail);
            userDTO.setStatus(Status.valueOf(status).name());
            userDAO.update(userDTO);
            logger.warn("Status after update is: " + userDTO.getStatus());

        } catch (NullPointerException | IllegalStateException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }

    }
}
