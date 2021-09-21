package dao.impl;

import controller.database.DBManager;
import dao.UserDAO;
import dto.UserDTO;
import entity.User;
import exception.DBException;
import org.apache.log4j.Logger;
import sql.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            while (userList.iterator().hasNext()) {
                UserDTO userDTO;
                userDTO = converter.convertFrom(userList.iterator().next());
                userDTOList.add(userDTO);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String emailFromDB;
        try {
            preparedStatement = connection.prepareStatement(Query.USER_EXIST_BY_LOGIN);
            preparedStatement.setString(1, name);
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getResultSet();
                emailFromDB = resultSet.getString(4);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
