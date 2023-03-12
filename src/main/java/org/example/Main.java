package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:postgresql://localhost:55000/postgres";
        String username = "postgres";
        String password = "postgrespw";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to PostgreSQL server");

            String sql = "INSERT INTO person (personid, firstname, secondname, address, city)" + " VALUES (1, 'Lionel', 'Messi', 'PSG', 'Argentina')";

            Statement statement = connection.createStatement();

            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                System.out.println("A new person has been added.");
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL DB");
            e.printStackTrace();
        } {

        }
    }
}
