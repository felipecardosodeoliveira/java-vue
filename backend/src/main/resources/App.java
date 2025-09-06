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

        server.createContext("/products", exchange -> {
            String method = exchange.getRequestMethod();
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equals(method)) {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            }

            if ("POST".equals(method)) {
                try (Connection conn = DB.getConnection()) {
                    ProductDAO productDAO = new ProductDAO(conn);
                    String body = new String(exchange.getRequestBody().readAllBytes());

                    Product product = gson.fromJson(body, Product.class);
                    productDAO.save(product);

                    byte[] response = gson.toJson(product).getBytes();
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(201, response.length);
                    exchange.getResponseBody().write(response);
                } catch (Exception e) {
                    exchange.sendResponseHeaders(500, 0);
                    exchange.getResponseBody().write("Error".getBytes());
                } finally {
                    exchange.close();
                }
            } else if ("GET".equals(method)) {
                try (Connection conn = DB.getConnection()) {
                    ProductDAO productDAO = new ProductDAO(conn);
                    List<Product> products = productDAO.getAll();

                    String responseJson = gson.toJson(products);
                    byte[] response = responseJson.getBytes();

                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, response.length);
                    exchange.getResponseBody().write(response);

                    System.out.println("Returned " + products.size() + " products");
                } catch (Exception e) {
                    exchange.sendResponseHeaders(500, 0);
                    exchange.getResponseBody().write("Error retrieving products".getBytes());
                } finally {
                    exchange.close();
                }
            } else {
                exchange.sendResponseHeaders(405, 0); 
                exchange.close();
            }
        });

        server.start();

        System.out.println("Server started on port 8080");
        System.out.println("Press Ctrl+C to stop the server");
    }
}
