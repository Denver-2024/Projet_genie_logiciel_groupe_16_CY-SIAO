package com.cy_siao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections.
 * Provides a method to establish a connection to a PostgreSQL database
 * using predefined configuration parameters.
 */
public class DatabaseUtil {

    private static final String URL = "jdbc:postgresql://postgresql-cy-siao.alwaysdata.net:5432/cy-siao_db";
    private static final String USERNAME = "cy-siao";
    private static final String PASSWORD = "Cy_siao@groupe16";

    /**
     * Connection to the database
     * @return The connection
     * @throws SQLException Exception with the database
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
