package jm.task.core.jdbc.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;

import jm.task.core.jdbc.model.User;

public class Util {
    public static Connection getConnection() {
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javatest",
                    "root",
                    "awR123qe");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Statement getStatement(Connection con) {
        try {
            return con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SessionFactory getSessionFactory() {
        Configuration conf = new Configuration();
        Properties settings = new Properties();
        ServiceRegistry sr;

        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, "jdbc:mysql://localhost:3306/javatest?useSSL=false");
        settings.put(Environment.USER, "root");
        settings.put(Environment.PASS, "awR123qe");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        conf.setProperties(settings);
        conf.addAnnotatedClass(User.class);

        sr = new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties()).build();
        return conf.buildSessionFactory(sr);
    }
}