package controller.database;

import exception.DBException;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DBManager {
    private static Logger LOGGER = Logger.getLogger(DBManager.class.getSimpleName());
    private final String RESOURCE_BUNDLE = "app";
    private final String DB_CONNECTION_URL = "db.connection.url";
    private final String DB_USER = "db.user";
    private final String DB_PASS = "db.password";
    private final String POOL_INIT_SIZE = "db.connection.pool.size";
    private static DBManager instance;
    private BasicDataSource ds;


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
            int poolSize = Integer.parseInt(resourceBundle.getString(POOL_INIT_SIZE));
            ds = new BasicDataSource();
            ds.setUrl(url);
            ds.setUsername(userName);
            ds.setPassword(pass);
            ds.setInitialSize(poolSize);

        } catch (NumberFormatException e) {
            LOGGER.warning(e.getMessage());
            throw new DBException("Can't connect to database", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void close(AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            throw new DBException("Can't close "+ closeable.getClass().getSimpleName(), e);
        }
    }

}
