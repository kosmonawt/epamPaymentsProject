package controller.database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBManager {
    private static Logger LOGGER = Logger.getLogger(DBManager.class.getSimpleName());
    private static volatile DBManager instance;
    private DataSource ds;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/payment");


        } catch (NamingException e) {
            throw new IllegalStateException("Cannot init DBManager", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}
