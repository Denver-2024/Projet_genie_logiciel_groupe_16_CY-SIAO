package com.cy_siao.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing database connections.
 * Provides a method to establish a connection to a PostgreSQL database
 * using predefined configuration parameters.
 */
public class DatabaseUtil {
    /**
     * Connection to the database
     * @return The connection
     */
    public static Connection getConnection() {
        Connection connection = null;

        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("URL");
            String username = prop.getProperty("USERNAME");
            String password = prop.getProperty("PASSWORD");

            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Error in getting connect to the database"+e.getMessage());
        }

        return connection;
    }
}
