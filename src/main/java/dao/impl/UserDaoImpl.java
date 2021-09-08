package dao.impl;

import controller.database.DBManager;
import dao.DAO;
import dto.UserDTO;
import entity.User;
import exception.DBException;
import sql.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoImpl implements DAO<UserDTO> {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getSimpleName());
    DBManager dbManager = DBManager.getInstance();

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
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return userDTOList;
    }

    @Override
    public UserDTO getById(Long id) {
        UserDTO userDTO = null;
        try {
            UserDaoConverter converter = new UserDaoConverter();
            User user = dbManager.getUser(id);
            userDTO = converter.convertFrom(user);
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return userDTO;
    }

    @Override
    public UserDTO getByName(String name) {
        return null;
    }

    @Override
    public void create(UserDTO userDTO) {
        UserDaoConverter converter = new UserDaoConverter();
        User user = converter.convertTo(userDTO);
        try {
            dbManager.insertUser(user);
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(UserDTO userDTO) {
        UserDaoConverter converter = new UserDaoConverter();
        User user = converter.convertTo(userDTO);
        try {
            dbManager.updateUser(user);
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            dbManager.deleteUser(id);
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean exist(String name) {
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.USER_EXIST_BY_LOGIN);
            preparedStatement.setString(1, "'" + name + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
