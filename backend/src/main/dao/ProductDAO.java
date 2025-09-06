package dao;

import entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(Product product) throws SQLException{
        String sql = "INSERT INTO products (name, price) VALUES(?, ?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("FAILED TO SAVE PRODUCT " + e.getMessage());
            throw e;
        }
    }

    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try(Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price")
                );
                products.add(product);
            }

            return  products;
        } catch (Exception e) {
            System.out.println("FAILED TO GET PRODUCTS " + e.getMessage());
            throw e;
        }
    }

}
