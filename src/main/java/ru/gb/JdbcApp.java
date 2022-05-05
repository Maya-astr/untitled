package ru.gb;

import java.sql.*;

public class JdbcApp {
    
    private static Connection connection;

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:javadb.db");
            Statement statement =  connection.createStatement()) {
           createTable(statement);
           insert(statement,"Georges", 95);
           insert(statement,"May", 90);
           insert(statement,"Jayn", 99);
           select(statement);
           selectByName(statement, "May");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void selectByName(Statement statement, String name) throws SQLException {
        final String query =String.format("SELECT * FROM students WHERE name ='%s'", name);
        
        final ResultSet rs = statement.executeQuery(query);
        while (rs.next())  {
            int id = rs.getInt("id");
            String nameDB = rs.getString("name");
            int score = rs.getInt("score");
            System.out.printf("%d - %s - %d\n", id, nameDB, score);

        }
        
    }

    private static void select(Statement statement) throws SQLException {
       final ResultSet rs = statement.executeQuery("SELECT * FROM students");
       while (rs.next())  {
          int id = rs.getInt("id");
          String name = rs.getString("name");
          int score = rs.getInt("score");
           System.out.printf("%d - %s - %d\n", id, name, score);

       }

    }

    private static void insert(Statement statement, String name, int score) throws SQLException {
       statement.executeUpdate("INSERT INTO students (name, score) VALUES ('" + name + "'," + score + ")");
        
    }

    private static void createTable(Statement statement) throws SQLException {
        statement.executeUpdate("" +
                "CREATE TABLE IF NOT  EXISTS  students (" +
                "  id INTEGER PRIMARY KEY  AUTOINCREMENT," +
                "  name  TEXT," +
                "  score INTEGER" +
                ")");
    }
}
