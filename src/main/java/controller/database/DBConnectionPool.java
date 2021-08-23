package controller.database;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConnectionPool {
    private static Logger LOGGER = Logger.getLogger(DBConnectionPool.class.getSimpleName());
    private static String CONNECTION_URL = "jdbc:postgresql://localhost/test";
    private static String DB_USERNAME = "postgres";
    private static String DB_PASSWORD = "kolobok";

    private static BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl(CONNECTION_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setMinIdle(1);
        dataSource.setMaxIdle(10);
        dataSource.setMaxActive(2);
        dataSource.setMaxWait(1);
        dataSource.setMaxOpenPreparedStatements(20);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private DBConnectionPool() {
    }

}
