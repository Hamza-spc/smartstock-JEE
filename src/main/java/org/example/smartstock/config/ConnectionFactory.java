package org.example.smartstock.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String DEFAULT_URL = "jdbc:h2:~/smartstock;AUTO_SERVER=TRUE;MODE=LEGACY";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private final String url;

    public ConnectionFactory() {
        this(DEFAULT_URL);
    }

    public ConnectionFactory(String url) {
        this.url = url;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, USERNAME, PASSWORD);
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }
}
