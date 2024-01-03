package ensa.edulinker.backend.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDBConnectionTest {

    private static Connection connection;

    @Before
    public void setUp() {
        connection = MySQLDBConnection.getConnection();
    }

    @After
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testDatabaseConnection() {
        assertNotNull("Database connection success", connection);
    }
}
