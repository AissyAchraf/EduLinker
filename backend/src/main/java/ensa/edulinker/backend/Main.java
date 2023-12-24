package ensa.edulinker.backend;

import ensa.edulinker.backend.dao.MySQLDBConnection;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        Connection connection = MySQLDBConnection.getConnection();

        System.out.println("connection : "+connection.toString());
    }
}
