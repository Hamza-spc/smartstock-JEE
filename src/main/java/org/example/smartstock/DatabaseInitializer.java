package org.example.smartstock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private final ConnectionFactory connectionFactory;

    public DatabaseInitializer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void initializeSchema() {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            for (String sqlStatement : loadStatements("/db/schema.sql")) {
                statement.execute(sqlStatement);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }

    private String[] loadStatements(String resourcePath) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }
            return content.toString().split(";");
        }
    }
}
