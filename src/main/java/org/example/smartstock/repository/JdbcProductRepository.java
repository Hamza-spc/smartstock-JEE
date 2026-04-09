package org.example.smartstock.repository;

import org.example.smartstock.config.ConnectionFactory;
import org.example.smartstock.domain.Product;
import org.example.smartstock.exception.ProductNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductRepository implements ProductRepository {
    private final ConnectionFactory connectionFactory;

    public JdbcProductRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO products (sku, name, quantity) VALUES (?, ?, ?)";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getSku());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save product", e);
        }
    }

    @Override
    public Product findBySku(String sku) {
        String sql = "SELECT sku, name, quantity FROM products WHERE sku = ?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, sku);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
                throw new ProductNotFoundException("Product not found: " + sku);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch product by sku", e);
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT sku, name, quantity FROM products";
        List<Product> products = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                products.add(mapRow(resultSet));
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch products", e);
        }
    }

    private Product mapRow(ResultSet resultSet) throws SQLException {
        String sku = resultSet.getString("sku");
        String name = resultSet.getString("name");
        int quantity = resultSet.getInt("quantity");
        return new Product(sku, name, quantity);
    }
}
