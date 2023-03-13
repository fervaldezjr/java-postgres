package org.example;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String...args) {
        final var jdbcURL         = "jdbc:postgresql://localhost:55000/postgres";
        final var username        = "postgres";
        final var password        = "postgrespw";
        final var insertPersonSql = "INSERT INTO person (personid, firstname, secondname, address, city) VALUES (?, ?, ?, ?, ?)";

        try (
            var connection = DriverManager.getConnection(jdbcURL, username, password);
            var statement  = connection.prepareStatement(insertPersonSql);
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
