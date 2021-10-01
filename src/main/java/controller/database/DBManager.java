package controller.database;

import entity.*;
import exception.DBException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import sql.Query;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            preparedStatement.setString(k, user.getStatus().name());
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
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
            preparedStatement.setString(k++, user.getStatus().name());
            preparedStatement.setLong(k++, user.getId());
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

    /**
     * Get from database all user accounts by user email
     *
     * @param email user email
     * @return All user accounts
     */

    public List<Account> getAllUserAccountsByEmail(String email) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> accounts = new ArrayList<>();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.ACCOUNT_GET_ALL_BY_USER_EMAIL);
            preparedStatement.setString(1, email);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    accounts.add(getAccountFromResultSet(resultSet));
                }
            }
            con.commit();
            return accounts;

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can not get accounts by user email", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
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

    /**
     * @param account Account to update
     */
    public void updateAccount(Account account) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            int k = 1;
            preparedStatement = con.prepareStatement(Query.ACCOUNT_UPDATE);
            preparedStatement.setString(k++, account.getUserLogin());
            preparedStatement.setBigDecimal(k++, account.getAmount());
            preparedStatement.setString(k++, account.getCurrency().name().trim().toUpperCase());
            preparedStatement.setString(k++, account.getStatus().name().trim().toUpperCase());
            preparedStatement.setLong(k++, account.getAccountNumber().longValue());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
        } finally {
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

    /**
     * Save Card entity to DB
     *
     * @param card save Card
     */

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

    public List<Card> getAllCardsByAccountNumber(Long accNumber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Card> cards = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Query.CARD_GET_ALL_BY_ACCOUNT_NUMBER);
            preparedStatement.setLong(1, accNumber);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    cards.add(getCardFromResultSet(resultSet));
                }
            }
            connection.commit();
            return cards;

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(connection);
            throw new DBException("Can't get cards by  : ", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
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
            preparedStatement.setLong(k++, payment.getPaymentNum().longValue());
            preparedStatement.setLong(k++, payment.getPaymentFromAccount().longValue());
            preparedStatement.setLong(k++, payment.getPaymentToAccount().longValue());
            preparedStatement.setString(k++, payment.getDateTime().toString());
            preparedStatement.setBigDecimal(k++, payment.getAmount());
            preparedStatement.setString(k++, payment.getPaymentStatus().name());
            preparedStatement.setString(k++, payment.getSender());
            preparedStatement.setString(k++, payment.getRecipient());
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    payment.setId(resultSet.getLong(1));
                }
            }
            con.commit();
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

    public Payment getPaymentByAccountFromId(BigInteger id) {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Payment payment = new Payment();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.PAYMENT_GET_BY_ACCOUNT_FROM_ID);
            preparedStatement.setLong(1, id.longValue());
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

        int k = 1;
        Payment payment = new Payment();
        payment.setId(resultSet.getLong(k++));
        payment.setPaymentNum(BigInteger.valueOf(resultSet.getLong(k++)));
        payment.setPaymentFromAccount(BigInteger.valueOf(resultSet.getLong(k++)));
        payment.setPaymentToAccount(BigInteger.valueOf(resultSet.getLong(k++)));
        payment.setDateTime(LocalDateTime.parse(resultSet.getString(k++)));
        payment.setAmount(resultSet.getBigDecimal(k++));
        payment.setPaymentStatus(PaymentStatus.valueOf(resultSet.getString(k++)));
        payment.setSender(resultSet.getString(k++));
        payment.setRecipient(resultSet.getString(k++));

        return payment;
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
        user.setStatus(Status.valueOf(resultSet.getString(k++)));
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

    public void updatePaymentStatus(Payment payment) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            int k = 1;
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.UPDATE_PAYMENT);
            preparedStatement.setLong(k++, payment.getPaymentFromAccount().longValue());
            preparedStatement.setLong(k++, payment.getPaymentToAccount().longValue());
            preparedStatement.setString(k++, payment.getDateTime().toString());
            preparedStatement.setBigDecimal(k++, payment.getAmount());
            preparedStatement.setString(k++, payment.getPaymentStatus().name().trim().toUpperCase());
            preparedStatement.setString(k++, payment.getSender());
            preparedStatement.setString(k++, payment.getRecipient());
            preparedStatement.setLong(k++, payment.getPaymentNum().longValue());
            preparedStatement.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Update payment failed ", e);
        } finally {
            close(preparedStatement);
            close(con);
        }

    }

    public void deletePayment(Long id) {
        //TODO
    }

    public synchronized BigInteger getNumberFromVault() {
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

    public List<Payment> getAllPaymentsByUserEmail(String email) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.PAYMENT_GET_BY_USER_EMAIL);
            preparedStatement.setString(1, email);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    payments.add(getPaymentFromResultSet(resultSet));
                }
            }
            con.commit();
            return payments;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can't get all payments by user email: " + email, e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }

    public BigDecimal getAmountByAccountNumber(Long accountNumber) {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BigDecimal amount = BigDecimal.ZERO;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.GET_AMOUNT_BY_ACCOUNT_NUMBER);
            preparedStatement.setLong(1, accountNumber);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    amount = BigDecimal.valueOf(resultSet.getDouble(1));
                }
            }
            con.commit();
            return amount;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can't get all amount from account " + accountNumber, e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }

    public void updateAccountAmountByAccountNumber(Long accountNumber, BigDecimal newAmount) {

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.UPDATE_ACCOUNT_AMOUNT_BY_ACCOUNT_NUMBER);
            preparedStatement.setBigDecimal(1, newAmount);
            preparedStatement.setLong(2, accountNumber);
            preparedStatement.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can't get all amount from account " + accountNumber, e);
        } finally {
            close(preparedStatement);
            close(con);
        }

    }

    public boolean findAccountByNumber(Long accountNumber) {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.ACCOUNT_FIND_BY_NUMBER);
            preparedStatement.setLong(1, accountNumber);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    result = true;
                }
            }
            con.commit();
            return result;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
        return result;
    }


    public Account getAccountByAccountNumber(Long accountNumber) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = new Account();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.ACCOUNT_FIND_BY_NUMBER);
            preparedStatement.setLong(1, accountNumber);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    account = getAccountFromResultSet(resultSet);
                }
            }
            con.commit();
            return account;

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can not get account by acc number", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }

    /**
     * Get all payments with given status from database
     *
     * @param status Payment status
     * @return all payments with given status
     */
    public List<Payment> getAllPaymentsByStatus(PaymentStatus status) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.PAYMENT_GET_ALL_BY_STATUS);
            preparedStatement.setString(1, status.name().trim().toUpperCase());
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    payments.add(getPaymentFromResultSet(resultSet));
                }
            }
            con.commit();
            return payments;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can not get account by acc number", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }

    /**
     * Get payment from DB by payment number if present
     *
     * @param paymentNumber payment number
     * @return payment from DB if present
     */
    public Payment getPaymentByPaymentNumber(Long paymentNumber) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Payment payment = new Payment();
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(Query.PAYMENT_GET_BY_PAYMENT_NUMBER);
            preparedStatement.setLong(1, paymentNumber);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    payment = getPaymentFromResultSet(resultSet);
                }
            }
            con.commit();
            return payment;

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            rollback(con);
            throw new DBException("Can not get payment by payment number: " + paymentNumber, e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(con);
        }
    }
}
