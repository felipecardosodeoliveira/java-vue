package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableProducts {
    public static void init() throws SQLException {
        try(Connection conn = DB.getConnection(); Statement stmt = conn.createStatement()) {
            String products = """
               CREATE TABLE IF NOT EXISTS products (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    price REAL NOT NULL
               )""";

            stmt.execute(products);
            System.out.println("TABLE PRODUCTS WAS CREATED WITH SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
