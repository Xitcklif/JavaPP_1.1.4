package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private final String user = "root";
    private final String pass = "awR123qe";
    private final String url = "jdbc:mysql://localhost:3306/javatest";
    private Connection con;
    private Statement st;

    public Util() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }

    public Statement getStatement() {
        return st;
    }
}