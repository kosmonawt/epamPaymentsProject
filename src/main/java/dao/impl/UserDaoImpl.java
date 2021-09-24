package dao.impl;

import controller.database.DBManager;
import dao.UserDAO;
import dto.UserDTO;
import entity.User;
import exception.DBException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * User Data Access Object
 * communication with database
 */

public class UserDaoImpl implements UserDAO<UserDTO> {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> userList;
        UserDaoConverter converter = new UserDaoConverter();

        try {
            userList = dbManager.getAllUsers();
            for (User user : userList) {
                userDTOList.add(converter.convertFrom(user));
            }
        } catch (DBException e) {
            print(e);
        }
        return userDTOList;
    }

    private <T extends Exception> void print(T e) {
        LOGGER.warn(e.getMessage());
        e.printStackTrace();
    }

    @Override
    public UserDTO getById(Long id) {
        UserDTO userDTO = new UserDTO();
        try {
            UserDaoConverter converter = new UserDaoConverter();
            User user = dbManager.getUser(id);
            userDTO = converter.convertFrom(user);
        } catch (DBException | NullPointerException e) {
            print(e);
        }
        return userDTO;
    }


    @Override
    public UserDTO getByEmail(String email) {
        UserDaoConverter converter = new UserDaoConverter();
        User user = new User();
        try {
            user = dbManager.getUserByLogin(email);
        } catch (DBException | NullPointerException e) {
            print(e);
        }
        return converter.convertFrom(user);
    }

    @Override
    public void create(UserDTO userDTO) {
        UserDaoConverter converter = new UserDaoConverter();
        User user = converter.convertTo(userDTO);
        try {
            dbManager.insertUser(user);
        } catch (DBException e) {
            print(e);
        }
    }

    @Override
    public void update(UserDTO userDTO) {
        UserDaoConverter converter = new UserDaoConverter();
        User user = converter.convertTo(userDTO);
        try {
            dbManager.updateUser(user);
        } catch (DBException e) {
            print(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            dbManager.deleteUser(id);
        } catch (DBException e) {
            print(e);
        }
    }

    @Override
    public boolean existByEmail(String name) {
//TODO
        return false;
    }


}
