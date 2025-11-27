//package org.example.squaregameapi.database;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
///**
// * This class is responsible for managing a database connection as a singleton.
// * It enables the creation, retrieval, and closure of a single instance of a database connection.
// * The connection properties (URL, username, password) are injected from the application configuration.
// */
//@Component
//public class DatabaseConnection {
//
//    // Unique instance (Singleton)
//    private static Connection connection;
//
//    // Property injection from application.properties
//    private static String url;
//    private static String username;
//    private static String password;
//
//    // Injection via @Value (Spring calls this constructor)
//    public DatabaseConnection(
//            @Value("${spring.datasource.url}") String url,
//            @Value("${spring.datasource.username}") String username,
//            @Value("${spring.datasource.password}") String password
//    ) {
//        DatabaseConnection.url = url;
//        DatabaseConnection.username = username;
//        DatabaseConnection.password = password;
//    }
//
//    /**
//     * Retrieves a singleton instance of the database connection. If the connection
//     * is null or closed, it establishes a new connection using the configured
//     * database URL, username, and password.
//     *
//     * @return the active database connection
//     * @throws SQLException if a database access error occurs or the connection
//     *         cannot be established
//     */
//    public static Connection getConnection() throws SQLException {
//        if (connection == null || connection.isClosed()) {
//            connection = DriverManager.getConnection(url, username, password);
//        }
//        return connection;
//    }
//
//    /**
//     * Closes the active database connection, if it exists and is not closed.
//     * @throws SQLException
//     */
//    public static void closeConnection() throws SQLException {
//        if (connection != null && !connection.isClosed()) {
//            connection.close();
//        }
//    }
//}
