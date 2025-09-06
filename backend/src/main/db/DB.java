package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:my_db.db";
        return DriverManager.getConnection(url);
    }
}
