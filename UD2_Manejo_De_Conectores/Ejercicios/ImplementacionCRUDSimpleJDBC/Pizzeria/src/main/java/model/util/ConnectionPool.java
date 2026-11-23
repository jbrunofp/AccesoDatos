package model.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

import org.apache.commons.dbcp2.BasicDataSource;


public class ConnectionPool {
    private static final String URL = "jdbc:mysql://localhost:3306/pizzeria";
    private static final String USER = "root";
    private static final String PASS = "Holaaloh3343.";

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        dataSource.setInitialSize(3);
        dataSource.setMaxTotal(10);
        dataSource.setMaxWait(Duration.ofSeconds(3));
    }

    private ConnectionPool() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() throws SQLException {
        dataSource.close();
    }
}
