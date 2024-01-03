package ensa.edulinker.backend.dao;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.sql.Connection;
import java.sql.DriverManager;

class MySQLDBConnection {

    private static Connection connection;
    private static Configuration config;
    private static String databaseUrl;
    private static String databaseUsername;
    private static String databasePassword;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            config = new Configurations().properties("configuration/database.properties");

            databaseUrl = config.getString("database.url");
            databaseUsername = config.getString("database.username");
            databasePassword = config.getString("database.password");

            connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private MySQLDBConnection() {

    }

    public static Connection getConnection() {
        return connection;
    }
}
