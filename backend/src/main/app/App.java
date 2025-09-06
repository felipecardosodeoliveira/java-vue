package app;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import dao.ProductDAO;
import db.CreateTableProducts;
import db.DB;
import entities.Product;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        CreateTableProducts.init();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        Gson gson = new Gson();

        server.createContext("/products", exchange ->  {
            if ("POST".equals(exchange.getRequestMethod())) {
                try (Connection conn = DB.getConnection()) {
                    ProductDAO productDAO = new ProductDAO(conn);
                    String body = new String(exchange.getRequestBody().readAllBytes());

                    Product product = gson.fromJson(body, Product.class);
                    productDAO.save(product);

                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(201, 0);
                    exchange.getResponseBody().write(gson.toJson(product).getBytes());

                } catch (Exception e) {
                    exchange.sendResponseHeaders(500, 0);
                    exchange.getResponseBody().write("Error".getBytes());
                } finally {
                    exchange.close();
                }
            }
            if ("GET".equals(exchange.getRequestMethod())) {
                try(Connection conn = DB.getConnection()) {
                    ProductDAO productDAO = new ProductDAO(conn);
                    List<Product> products = productDAO.getAll();

                    String responseJson = gson.toJson(products);

                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(201, 0);
                    exchange.getResponseBody().write(responseJson.getBytes());

                    System.out.println("Returned " + products.size() + " products");
                } catch (Exception e) {
                    exchange.sendResponseHeaders(500, 0);
                    exchange.getResponseBody().write("Error retrieving products".getBytes());
                } finally {
                    exchange.close();
                }
            }
        });

        server.start();

        System.out.println("Server started on port 8080");
        System.out.println("Press Ctrl+C to stop the server");

    }
}
