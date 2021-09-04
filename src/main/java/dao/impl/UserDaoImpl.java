package dao.impl;

import controller.database.DBManager;
import dao.UserDAO;
import dto.UserDTO;
import entity.User;
import sql.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDAO {
    private static Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getSimpleName());
    DBManager dbManager = DBManager.getInstance();

    public UserDaoImpl() {
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUser(long id) {
        return null;
    }

    @Override
    public void save(UserDTO userDTO) {
        UserDaoConverter converter = new UserDaoConverter();
        User user = converter.convertTo(userDTO);

        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.USER_SAVE)) {


        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }


    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUSer(User user) {

    }

    @Override
    public boolean existByLogin(String login) {

        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.USER_EXIST_BY_LOGIN);
            preparedStatement.setString(1, "'" + login + "'");
            ResultSet resultSet = preparedStatement.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
