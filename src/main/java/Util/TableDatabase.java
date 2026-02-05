package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableDatabase {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "24022002";
    private static final String DATABASE_NAME = "skillbox_hibernate";

    public static void initDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            //noinspection SqlDialectInspection
            statement.execute("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
            System.out.println("DATABASE checked/created: " + DATABASE_NAME);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
