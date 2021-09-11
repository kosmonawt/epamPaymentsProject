package controller.database;

import entity.*;
import exception.DBException;
import org.apache.commons.dbcp.BasicDataSource;
import sql.Query;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DBManager {
    private static final Logger LOGGER = Logger.getLogger(DBManager.class.getSimpleName());
    private static final String RESOURCE_BUNDLE = "app";
    private static final String DB_CONNECTION_URL = "db.connection.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASS = "db.password";
    private static final String POOL_INIT_SIZE = "db.connection.pool.size";
    private static final String DRIVER = "db.driver.class";
    private static DBManager instance;
    private final BasicDataSource ds;


    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);

            String url = resourceBundle.getString(DB_CONNECTION_URL);
            String userName = resourceBundle.getString(DB_USER);
            String pass = resourceBundle.getString(DB_PASS);
            String driver = resourceBundle.getString(DRIVER);
            int poolSize = Integer.parseInt(resourceBundle.getString(POOL_INIT_SIZE));
            ds = new BasicDataSource();
            ds.setUrl(url);
            ds.setUsername(userName);
            ds.setPassword(pass);
            ds.setInitialSize(poolSize);
            ds.setDefaultAutoCommit(false);
            ds.setDriverClassName(driver);

        } catch (NumberFormatException e) {
            LOGGER.warning(e.getMessage());
            throw new DBException("Can't connect to database", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public List<User> getAllUsers() {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.USER_GET_ALL);
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    users.add(getUserFromResultSet(resultSet));
                }
            }
            con.commit();
            return users;
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("Can't get Users from database", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }

    public User getUserByLogin(String email) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.USER_EXIST_BY_LOGIN);
            int k = 1;
            preparedStatement.setString(k, email);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    user = getUserFromResultSet(resultSet);
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("User with email '" + email + "' Does not exist in database", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
        return user;
    }

    public User getUser(@NotNull Long id) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.USER_GET_BY_ID);
            int k = 1;
            preparedStatement.setString(k, String.valueOf(id));
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    user = getUserFromResultSet(resultSet);
                }
            }
            con.commit();
            return user;
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("User with ID= " + id + " Does not exist in database", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }

    public void insertUser(@NotNull User user) throws DBException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.USER_CREATE, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setString(k++, user.getName());
            preparedStatement.setString(k++, user.getSurname());
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setString(k++, user.getPassword());
            preparedStatement.setString(k++, user.getRole().name());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(1));
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("Can't insert user to database", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
    }

    public void updateUser(@NotNull User user) {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.USER_UPDATE);
            int k = 1;
            preparedStatement.setString(k++, user.getName());
            preparedStatement.setString(k++, user.getSurname());
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setString(k++, user.getPassword());
            preparedStatement.setString(k++, user.getRole().name());
            preparedStatement.setString(k++, String.valueOf(user.getId()));
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("Can't update user in database", e);
        } finally {
            close(preparedStatement);
            close(con);
        }

    }

    public void deleteUser(@NotNull Long id) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.USER_DELETE);
            int k = 1;
            preparedStatement.setInt(k, id.intValue());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("User with ID= " + id + " Does not exist in database", e);
        } finally {
            close(preparedStatement);
            close(con);
        }
    }


    public Role getRoleByName(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Query.ROLE_GET_BY_NAME);
            preparedStatement.setString(1, name);
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getResultSet();
                return Role.valueOf(resultSet.getString(2));
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(connection);
            throw new DBException("Role with name: " + name + " does not exist in database", e);

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return null;
    }


    public Role getRoleById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Query.ROLE_GET_BY_ID);
            preparedStatement.setString(1, String.valueOf(id));
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getResultSet();
                return Role.valueOf(resultSet.getString(2));
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(connection);
            throw new DBException("Role with id: " + id + " does not exist in database", e);

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return null;
    }

    public Card getCardById(Long id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Card card = new Card();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Query.CARD_GET_BY_ID);
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getResultSet();
                return getCardFromResultSet(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(connection);
            throw new DBException("Role with id: " + id + " does not exist in database", e);

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return card;
    }

    public Card getCardByCardNumber(String cardNumber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Card card = new Card();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Query.CARD_GET_BY_CARD_NUMBER);
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getResultSet();
                return getCardFromResultSet(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(connection);
            throw new DBException("Role with card number: " + cardNumber + " does not exist in database", e);

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return card;
    }

    public void createCard(Card card) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.CARD_CREATE, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setInt(k++, card.getCardNumber().intValue());
            preparedStatement.setInt(k++, card.getPin());
            preparedStatement.setInt(k++, card.getCvv());
            preparedStatement.setString(k++, card.getExpiryDate().toString());
            preparedStatement.setString(k++, card.getCardType().name());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    card.setId(resultSet.getLong(1));
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("Can not create card", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }

    public List<Card> getAllCards() {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Card> cardList = new ArrayList<>();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.CARD_GET_ALL);
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    cardList.add(getCardFromResultSet(resultSet));
                }
            }
            return cardList;
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("Can't get all cards", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }

    public void updateCard(Card card) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.CARD_UPDATE);
            int k = 1;
            preparedStatement.setInt(k++, card.getCardNumber().intValue());
            preparedStatement.setInt(k++, card.getPin());
            preparedStatement.setInt(k++, card.getCvv());
            preparedStatement.setString(k++, card.getExpiryDate().toString());
            preparedStatement.setString(k++, card.getCardType().name());
            preparedStatement.setInt(k++, card.getId().intValue());
            preparedStatement.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("Can't update card with card number: " + card.getCardNumber() + " and id: " + card.getId(), e);
        } finally {
            close(preparedStatement);
            close(con);
        }

    }

    public void deleteCard(Long id) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.CARD_DELETE);
            preparedStatement.setInt(1, id.intValue());
            preparedStatement.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("Can't delete card with id: " + id, e);
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    public void createPayment(Payment payment) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.PAYMENT_CREATE);
            int k = 1;
            preparedStatement.setInt(k++, payment.getPaymentFromAccount().getId().intValue());
            preparedStatement.setInt(k++, payment.getPaymentToAccount().getId().intValue());
            preparedStatement.setTimestamp(k++, Timestamp.valueOf(payment.getDateTime()));
            preparedStatement.setBigDecimal(k++, payment.getAmount());
            preparedStatement.setString(k++, payment.toString());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    payment.setId(resultSet.getLong(1));
                }
            }

        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("Can't create payment : " + payment.toString(), e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }


    }

    public Payment getPaymentByAccountFromId(Long id) {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Payment payment = new Payment();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.PAYMENT_GET_BY_ACCOUNT_FROM_ID);
            preparedStatement.setInt(1, id.intValue());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getResultSet();
                payment = getPaymentFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            rollback(con);
            throw new DBException("Can't get payment by account id : " + id, e);
        }

        return null;
    }

    private Payment getPaymentFromResultSet(ResultSet resultSet) throws SQLException {
//TODO when Account DAO would be implemented

        return null;
    }

    private Card getCardFromResultSet(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getLong(1));
        card.setCardNumber(BigInteger.valueOf(resultSet.getLong(2)));
        card.setPin(resultSet.getInt(3));
        card.setCvv(resultSet.getInt(4));
        card.setExpiryDate(LocalDate.parse(resultSet.getString(5)));
        card.setCardType(CardType.valueOf(resultSet.getString(6).trim().toUpperCase()));
        return card;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        int k = 1;
        User user = new User();
        user.setId(resultSet.getLong(k++));
        user.setName(resultSet.getString(k++));
        user.setSurname(resultSet.getString(k++));
        user.setEmail(resultSet.getString(k++));
        user.setPassword(resultSet.getString(k++));
        user.setRole(Role.valueOf(resultSet.getString(k++)));
        return user;
    }

    private static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LOGGER.warning(e.getMessage());
                throw new DBException("Can't close " + closeable.getClass().getSimpleName(), e);
            }
        }
    }

    private static void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.warning(e.getMessage());
                throw new DBException("Can't rollback connection ", e);
            }
        }
    }

    public void deletePayment(Long id) {
        //TODO
    }
}
