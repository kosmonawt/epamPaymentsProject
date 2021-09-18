package controller.database;

import entity.*;
import exception.DBException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import sql.Query;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Database Manager, include CRUD low level method for communication with database
 * Only the required DAO methods are defined!
 * <p>
 * SINGLETON
 */
public class DBManager {
    private static final Logger LOGGER = Logger.getLogger(DBManager.class);
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
            LOGGER.warn(e.getMessage());
            throw new DBException("Can't connect to database", e);
        }
    }


    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in your
     * app.properties
     *
     * @return A DB connection.
     */

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
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    users.add(getUserFromResultSet(resultSet));
                }
            }
            con.commit();
            return users;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
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
        User user = new User();
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
            LOGGER.warn(e.getMessage());
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
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    user = getUserFromResultSet(resultSet);
                }
            }
            con.commit();
            return user;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
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
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(1));
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
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
            preparedStatement.setInt(k++, Math.toIntExact(user.getId()));
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException | ArithmeticException e) {
            LOGGER.warn(e.getMessage());
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
            LOGGER.warn(e.getMessage());
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
            LOGGER.warn(e.getMessage());
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
            LOGGER.warn(e.getMessage());
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
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                return getCardFromResultSet(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
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
            preparedStatement.setLong(1, Long.parseLong(cardNumber));
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    card = getCardFromResultSet(resultSet);
                }
            }
            connection.commit();
            return card;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(connection);
            throw new DBException("Card with card number: " + cardNumber + " does not exist in database", e);

        } catch (NumberFormatException e) {
            LOGGER.warn("Incorrect number input in \" getCardByCardNumber \" ");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return card;
    }

    public List<Account> getAllUserAccountsById(Long id) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> accounts = new ArrayList<>();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.ACCOUNT_GET_ALL_BY_USER_ID);
            preparedStatement.setInt(1, id.intValue());
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    accounts.add(getAccountFromResultSet(resultSet));
                    //TODO chek impl of get list of object from result set (stream ?)
                }
            }
            con.commit();
            return accounts;

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can not create card", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }

    public void createAccount(Account account) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.ACCOUNT_CREATE, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setLong(k++, account.getAccountNumber().longValue());
            preparedStatement.setString(k++, account.getUserLogin());
            preparedStatement.setBigDecimal(k++, account.getAmount());
            preparedStatement.setString(k++, account.getCurrency().name());
            preparedStatement.setString(k++, account.getStatus().name());

            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    account.setId(resultSet.getLong(1));
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can not create account", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }

    }

    public void createCard(Card card) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.CARD_CREATE, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setLong(k++, card.getCardNumber().longValue());
            preparedStatement.setInt(k++, card.getPin());
            preparedStatement.setInt(k++, card.getCvv());
            preparedStatement.setString(k++, card.getExpiryDate().toString());
            preparedStatement.setString(k++, card.getCardType().name());
            preparedStatement.setLong(k++, card.getAccountNum().longValue());
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    card.setId(resultSet.getLong(1));
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
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
            LOGGER.warn(e.getMessage());
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
            preparedStatement.setLong(k++, card.getCardNumber().longValue());
            preparedStatement.setInt(k++, card.getPin());
            preparedStatement.setInt(k++, card.getCvv());
            preparedStatement.setString(k++, card.getExpiryDate().toString());
            preparedStatement.setString(k++, card.getCardType().name());
            preparedStatement.setInt(k++, card.getId().intValue());
            preparedStatement.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
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
            LOGGER.warn(e.getMessage());
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
            preparedStatement.setLong(k++, payment.getPaymentFromAccount().longValue());
            preparedStatement.setLong(k++, payment.getPaymentToAccount().longValue());
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
            LOGGER.warn(e.getMessage());
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
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                payment = getPaymentFromResultSet(resultSet);
            }
            con.commit();
            return payment;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can't get payment by account id : " + id, e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }

    }

    private Payment getPaymentFromResultSet(ResultSet resultSet) throws SQLException {
        //TODO when Account DAO would be implemented

        return null;
    }


    /**
     * return Account entity from database if user exist or empty user if it doesn't
     *
     * @param resultSet result set from prepared statement
     * @return return Account entity from database
     * @throws SQLException
     */

    private Account getAccountFromResultSet(ResultSet resultSet) throws SQLException {
        int k = 1;
        Account account = new Account();
        account.setId(resultSet.getLong(k++));
        account.setAccountNumber(BigInteger.valueOf(resultSet.getLong(k++)));
        account.setUserLogin(resultSet.getString(k++));
        account.setAmount(resultSet.getBigDecimal(k++));
        account.setCurrency(Currency.valueOf(resultSet.getString(k++)));
        account.setStatus(Status.valueOf(resultSet.getString(k++).toUpperCase()));
        return account;
    }

    /**
     * return Card entity from database if user exist or empty user if it doesn't
     *
     * @param resultSet result set from prepared statement
     * @return return Card entity from database
     * @throws SQLException
     */
    private Card getCardFromResultSet(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getLong(1));
        card.setCardNumber(BigInteger.valueOf(resultSet.getLong(2)));
        card.setPin(resultSet.getInt(3));
        card.setCvv(resultSet.getInt(4));
        card.setExpiryDate(LocalDate.parse(resultSet.getString(5)));
        card.setCardType(CardType.valueOf(resultSet.getString(6).trim().toUpperCase()));
        card.setAccountNum(BigInteger.valueOf(resultSet.getLong(7)));
        return card;
    }

    /**
     * return User entity from database if user exist or empty user if it doesn't
     *
     * @param resultSet - result set from prepared statement
     * @return new User from result set
     * @throws SQLException
     */

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

    /**
     * Close given instance of AutoCloseable class
     *
     * @param closeable must be an instance of AutoCloseable
     */

    private static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
                throw new DBException("Can't close " + closeable.getClass().getSimpleName(), e);
            }
        }
    }

    /**
     * Rollback given connection
     *
     * @param connection to rollback
     */

    private static void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.warn(e.getMessage());
                throw new DBException("Can't rollback connection ", e);
            }
        }
    }

    public void deletePayment(Long id) {
        //TODO
    }

    public BigInteger getNumberFromVault() {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BigInteger result = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.VAULT);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                result = BigInteger.valueOf((resultSet.getLong(1)));
            }

            con.commit();
            return result;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can't get number from vault ", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }
}
