package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String jdbcURL         = "jdbc:postgresql://localhost:55000/postgres";
        String username        = "postgres";
        String password        = "postgrespw";
        String insertPersonSql = "INSERT INTO person (personid, firstname, secondname, address, city) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection connection       = DriverManager.getConnection(jdbcURL, username, password);
            PreparedStatement statement = connection.prepareStatement(insertPersonSql);
        ){
            statement.setInt(1, 1);
            statement.setString(2, "Lionel");
            statement.setString(3, "Messi");
            statement.setString(4, "Argentina");

            if (statement.executeUpdate() > 0) {
                System.out.println("A new person has been added.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
